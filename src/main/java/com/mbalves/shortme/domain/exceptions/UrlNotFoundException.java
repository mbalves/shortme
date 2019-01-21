package com.mbalves.shortme.domain.exceptions;

import org.springframework.core.NestedRuntimeException;

public class UrlNotFoundException extends NestedRuntimeException {
    private String urlId;

    public UrlNotFoundException(String message, String badUrl) {
        super(message);
        this.urlId = badUrl;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }
}
