package com.mbalves.shortme.domain;

import java.util.Date;

public class UrlData {
    private String idShortUrl;
    private String userIp;
    private String userCity;
    private String userCountry;
    private Date creationDate;

    public String getIdShortUrl() {
        return idShortUrl;
    }

    public void setIdShortUrl(String idShortUrl) {
        this.idShortUrl = idShortUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
