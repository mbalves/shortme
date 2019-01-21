package com.mbalves.shortme.domain;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Url {

    @Id
    private String _id;
    private String fullUrl;
    private String shortUrl;
    private Date creationDate;
    private Long usage;

    public Url() {};

    public Url(String id, String fullUrl, String shortUrl) {
        this._id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
        this.creationDate = new Date();
    }

    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getUsage() {
        return usage;
    }

    public void setUsage(Long usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "Url{" +
                "Id='" + _id + '\'' +
                ", fullUrl='" + fullUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                '}';
    }
}
