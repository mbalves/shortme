package com.mbalves.shortme.business;

import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.domain.exceptions.IdNotFoundException;
import com.mbalves.shortme.repository.UrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    public void getAll_OK() {
        List<Url> urlObjects = Collections.singletonList(new Url(SOME_ID,SOME_URL,SOME_LINK));
        when(repository.findAll()).thenReturn(urlObjects);

        assertThat(urlService.getAll()).isEqualTo(urlObjects);
    }

    @Test
    public void delete_OK() {
        urlService.delete(SOME_ID);
    }


    @Test
    public void getFullUrl_NOT_FOUND() {
        Url urlObject = null;
        when(repository.findBy_id(anyString())).thenReturn(urlObject);

        assertThatThrownBy(() -> urlService.getFullUrl(SOME_ID)).isInstanceOf(IdNotFoundException.class);
    }
}