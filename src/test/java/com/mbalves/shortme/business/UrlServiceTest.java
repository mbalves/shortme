package com.mbalves.shortme.business;

import com.mbalves.shortme.domain.Statistics;
import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.repository.UrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UrlServiceTest {

    private static final String SOME_ID = "aaaaBB";
    private static final String SOME_URL = "http://someplace.com/stuffs/cool.html";
    private static final String BASE_URL = "http://short.me/api/shorturls";
    private static final String SOME_LINK = "http://short.me/aaaBB";

    private UrlService urlService;

    @Mock
    private UrlRepository repository;

    @Before
    public void setup(){
        initMocks(this);
        urlService = new UrlService(repository);
    }

    @Test
    public void getShortUrl_OK() {
        Url urlObject = new Url(SOME_ID,SOME_URL,SOME_LINK);
        when(repository.save(any(Url.class))).thenReturn(urlObject);

        assertThat(urlService.getShortUrl(SOME_URL,BASE_URL)).isEqualTo(urlObject);
    }

    @Test
    public void getFullUrl_OK() {
        Url urlObject = new Url(SOME_ID,SOME_URL,SOME_LINK);
        when(repository.findBy_id(anyString())).thenReturn(urlObject);

        assertThat(urlService.getFullUrl(SOME_ID)).isEqualTo(SOME_URL);
    }

    @Test
    public void findAll_OK() {
        Pageable pageable = PageRequest.of(0,10);
        Page<Url> pageObjects = new PageImpl<>(Collections.singletonList(new Url(SOME_ID,SOME_URL,SOME_LINK))
                                        ,pageable,1);
        when(repository.findAll(any(Pageable.class))).thenReturn(pageObjects);

        assertThat(urlService.findAll(0)).isEqualTo(pageObjects);
    }

    @Test
    public void delete_OK() {
        urlService.delete(SOME_ID);
    }

    @Test
    public void statistics_OK() {
        Date now = new Date();
        Url urlObject = new Url(SOME_ID,SOME_URL,SOME_LINK);
        urlObject.setCreationDate(now);
        Statistics stats = new Statistics(now, now,1L,1L);
        when(repository.count()).thenReturn(1L);
        when(repository.findFirstByOrderByCreationDateAsc()).thenReturn(urlObject);
        when(repository.findFirstByOrderByCreationDateDesc()).thenReturn(urlObject);
        when(repository.findAllAfterCreationDate(any(Date.class))).thenReturn(Collections.singletonList(new Url()));

        assertThat(urlService.getStatistics()).isNotNull();
        assertThat(urlService.getStatistics().getStartDate()).isEqualTo(stats.getStartDate());
        assertThat(urlService.getStatistics().getLastChange()).isEqualTo(stats.getLastChange());
        assertThat(urlService.getStatistics().getQuantity()).isEqualTo(stats.getQuantity());
        assertThat(urlService.getStatistics().getQuantityLastDay()).isEqualTo(stats.getQuantityLastDay());
    }

    @Test
    public void getFullUrl_NOT_FOUND() {
        when(repository.findBy_id(anyString())).thenReturn(null);

        assertThat(urlService.getFullUrl(SOME_ID)).isEqualTo(null);
    }
}