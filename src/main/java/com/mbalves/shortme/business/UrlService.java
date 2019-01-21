package com.mbalves.shortme.business;

import com.mbalves.shortme.domain.IpApiResponse;
import com.mbalves.shortme.domain.Statistics;
import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.domain.UrlData;
import com.mbalves.shortme.repository.UrlDataRepository;
import com.mbalves.shortme.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class UrlService {
    Logger logger = LoggerFactory.getLogger(UrlService.class);
    private final static RestTemplate restTemplate = new RestTemplate();

    private final int    ID_LENGHT = 6;
    private final String ID_DIGITS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private UrlRepository urlRepository;
    private UrlDataRepository dataRepository;

    @Autowired
    public UrlService(UrlRepository mongoRepository, UrlDataRepository dataRepository) {
        this.urlRepository = mongoRepository;
        this.dataRepository = dataRepository;
    }

    public Url getShortUrl(String fullUrl, String requestUrl){
        String newId = getRandomId();
        logger.info("New ID generated: " + newId);
        return urlRepository.save(new Url(newId, fullUrl,getBaseUrl(requestUrl)+newId));
    }

    public String getFullUrl(String _id, String clientIp) {
        logger.info("Searching key: " + _id);
        Url url = urlRepository.findBy_id(_id);
        if(url == null) return null;
        UrlData data = new UrlData();
        data.setIdShortUrl(_id);
        data.setCreationDate(new Date());
        data.setUserIp(clientIp);
        IpApiResponse geoLocation = getGeoLocation(clientIp);
        data.setUserCountry(geoLocation.getCountry());
        data.setUserCity(geoLocation.getCity());
        dataRepository.save(data);
        url.setUsage((url.getUsage()==null ? 0 : url.getUsage()) + 1);
        urlRepository.save(url);
        return url.getFullUrl();
    }

    private IpApiResponse getGeoLocation(String clientIP){
        String resourceUrl = "http://ip-api.com/json/";
        ResponseEntity<IpApiResponse> response = restTemplate.getForEntity(resourceUrl + clientIP, IpApiResponse.class);
        return response.getBody();
    }

    public Page<Url> findAll(int page) {
        return urlRepository.findAll(PageRequest.of(page, 10));
    }

    public void delete(String _id){
        urlRepository.deleteById(_id);
    }

    private String getRandomId() {
        String newId = "";
        for (int i = 0; i < ID_LENGHT; i++)
            newId += ID_DIGITS.charAt((int) Math.floor(Math.random() * ID_DIGITS.length()));
        return newId;
    }

    private String getBaseUrl(String requestUrl) {
        int index = requestUrl.lastIndexOf("/api/");
        return requestUrl.substring(0, index + 1);
    }

    public Statistics getStatistics() {
        Statistics stats = new Statistics();
        stats.setQuantity(urlRepository.count());


        Url first = urlRepository.findFirstByOrderByCreationDateAsc();
        stats.setStartDate(first.getCreationDate());

        Url last = urlRepository.findFirstByOrderByCreationDateDesc();
        stats.setLastChange(last.getCreationDate());

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(last.getCreationDate());

        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        List<Url> lastList = urlRepository.findAllAfterCreationDate(calendar.getTime());
        stats.setQuantityLastDay((long) lastList.size());
        return stats;
    }

    public Url findById(String id) {
        logger.info("Searching UrlId: " + id);
        return urlRepository.findBy_id(id);
    }

    public List<UrlData> getData(String id) {
        logger.info("Searching Data for UrlId: " + id);
        return dataRepository.findByIdShortUrl(id);
    }
}
