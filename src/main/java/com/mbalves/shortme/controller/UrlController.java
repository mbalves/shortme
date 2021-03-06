package com.mbalves.shortme.controller;

import com.mbalves.shortme.business.UrlService;
import com.mbalves.shortme.domain.Statistics;
import com.mbalves.shortme.domain.Url;
import com.mbalves.shortme.domain.UrlData;
import com.mbalves.shortme.domain.exceptions.BadURLException;
import com.mbalves.shortme.domain.exceptions.UrlNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class UrlController {
    Logger logger = LoggerFactory.getLogger(UrlController.class);

    private final String NOT_FOUND = "/resources/error.html";

    private UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @ResponseBody
    @PostMapping(value = "/api/shorturls", consumes = {APPLICATION_JSON_UTF8_VALUE})
    public Url getShortUrl(@RequestBody Url urlPair, HttpServletRequest request) {
        logger.info("URL received: "+urlPair.getFullUrl());
        return urlService.getShortUrl(validateUrl(urlPair.getFullUrl()), request.getRequestURL().toString());
    }

    @GetMapping(value = "/{id}")
    public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        String redirectUrlString = urlService.getFullUrl(id, getClientIp(request));
        logger.info("Original URL: " + redirectUrlString);
        if (redirectUrlString==null) {
            throw new UrlNotFoundException("URLID not found!",id);
        }
        return new RedirectView(redirectUrlString);
    }

    @ResponseBody
    @GetMapping(value = "/api/shorturls")
    public Page<Url> findAll(@RequestParam(required = false) Integer page) {
        page = (page==null) ? 0 : page - 1;
        return urlService.findAll(page);
    }

    @ResponseBody
    @GetMapping(value = "/api/shorturls/{id}")
    public Url findById(@PathVariable String id) {
        return urlService.findById(id);
    }

    @ResponseBody
    @GetMapping(value = "/api/data")
    public Statistics getStatistics() {
        return urlService.getStatistics();
    }

    @ResponseBody
    @GetMapping(value = "/api/data/{id}")
    public List<UrlData> getData(@PathVariable String id) {
        return urlService.getData(id);
    }

    @RequestMapping(value = "/api/shorturls/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        urlService.delete(id);
    }

    private String validateUrl(String fullUrl) {
        try{
            URL url = new URL(fullUrl);
            url.toURI();
        } catch (Exception e) {
            throw new BadURLException("Invalid URL!", fullUrl);
        }
        return fullUrl;
    }

    private String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            } else {
                remoteAddr = remoteAddr.split(",")[0];
            }
        }

        return remoteAddr;
    }
}
