package com.mbalves.shortme.domain.exceptions;

import org.springframework.core.NestedRuntimeException;

public class IdNotFoundException extends NestedRuntimeException {
    private String id;

    public IdNotFoundException(String message, String id) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
