package com.mbalves.shortme.business;

import com.mbalves.shortme.domain.Statistics;
import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class UrlService {
    Logger logger = LoggerFactory.getLogger(UrlService.class);

    private final int    ID_LENGHT = 6;
    private final String ID_DIGITS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private UrlRepository mongoRepository;

    @Autowired
    public UrlService(UrlRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    public Url getShortUrl(String fullUrl, String requestUrl){
        String newId = getRandomId();
        logger.info("New ID generated: " + newId);
        return mongoRepository.save(new Url(newId, fullUrl,getBaseUrl(requestUrl)+newId));
    }

    public String getFullUrl(String _id) {
        logger.info("Searching key: " + _id);
        Url pair = mongoRepository.findBy_id(_id);
        if(pair == null) return null;
        return pair.getFullUrl();
    }

    public Page<Url> findAll(int page) {
        return mongoRepository.findAll(PageRequest.of(page, 10));
    }

    public void delete(String _id){
        mongoRepository.deleteById(_id);
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
        stats.setQuantity(mongoRepository.count());


        Url first = mongoRepository.findFirstByOrderByCreationDateAsc();
        stats.setStartDate(first.getCreationDate());

        Url last = mongoRepository.findFirstByOrderByCreationDateDesc();
        stats.setLastChange(last.getCreationDate());

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(last.getCreationDate());

        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        List<Url> lastList = mongoRepository.findAllAfterCreationDate(calendar.getTime());
        stats.setQuantityLastDay((long) lastList.size());
        return stats;
    }

}
