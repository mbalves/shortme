package com.mbalves.shortme.domain.exceptions;

import org.springframework.core.NestedRuntimeException;

public class BadURLException extends NestedRuntimeException {
    private String badUrl;

    public BadURLException(String message, String badUrl) {
        super(message);
        this.badUrl = badUrl;
    }

    public String getBadUrl() {
        return badUrl;
    }

    public void setBadUrl(String badUrl) {
        this.badUrl = badUrl;
    }
}
