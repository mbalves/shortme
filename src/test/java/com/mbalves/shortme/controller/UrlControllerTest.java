package com.mbalves.shortme.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbalves.shortme.config.GlobalErrorHandler;
import com.mbalves.shortme.domain.Statistics;
import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.repository.UrlRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlControllerTest {

    private static final String SOME_ID = "aaaaBB";
    private static final String SOME_URL = "http://someplace.com/stuffs/cool.html";
    private static final String SOME_LINK = "http://short.me/aaaBB";

    @MockBean
    private UrlRepository repository;

    @Autowired
    private UrlController controller;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new GlobalErrorHandler()).build();
    }

    @Test
    public void getShortUrl_OK() throws Exception {
        Url requestUrl = new Url(null,SOME_URL,null);

        Url returnUrl = new Url(SOME_ID,SOME_URL,SOME_LINK);
        given(repository.save(any(Url.class))).willReturn(returnUrl);

        mockMvc.perform(post("/api/shorturls")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(asJsonString(requestUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._id").value(SOME_ID));
    }

    @Test
    public void getAll_OK() throws Exception {
        Pageable pageable = PageRequest.of(0,10);
        Page<Url> pageObjects = new PageImpl<>(Collections.singletonList(new Url(SOME_ID,SOME_URL,SOME_LINK))
                                        ,pageable,1);
        given(repository.findAll(any(Pageable.class))).willReturn(pageObjects);

        mockMvc.perform(get("/api/shorturls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    public void getStatistics_OK() throws Exception {

        Date now = new Date();
        Url urlObject = new Url(SOME_ID,SOME_URL,SOME_LINK);
        urlObject.setCreationDate(now);
        Statistics stats = new Statistics(now, now,1L,1L);
        given(repository.count()).willReturn(1L);
        given(repository.findFirstByOrderByCreationDateAsc()).willReturn(urlObject);
        given(repository.findFirstByOrderByCreationDateDesc()).willReturn(urlObject);
        given(repository.findAllAfterCreationDate(any(Date.class))).willReturn(Collections.singletonList(new Url()));

        mockMvc.perform(get("/api/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(1));
    }

    @Test
    public void delete_OK() throws Exception {
        mockMvc.perform(delete("/api/shorturls/{id}",SOME_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void redirectUrl() throws Exception {
        Url returnUrl = new Url(SOME_ID,SOME_URL,SOME_LINK);
        given(repository.findBy_id(anyString())).willReturn(returnUrl);

        mockMvc.perform(get("/{id}",SOME_ID))
                .andExpect(status().is3xxRedirection());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}