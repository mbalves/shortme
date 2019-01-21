package com.mbalves.shortme.domain;

public class IpApiResponse {
    private String as;
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private float lat;
    private float lon;
    private String org;
    private String query;
    private String region;
    private String regionName;
    private String status;
    private String timezone;
    private String zip;


    // Getter Methods

    public String getAs() {
        return as;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getIsp() {
        return isp;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getOrg() {
        return org;
    }

    public String getQuery() {
        return query;
    }

    public String getRegion() {
        return region;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getStatus() {
        return status;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getZip() {
        return zip;
    }

    // Setter Methods

    public void setAs(String as) {
        this.as = as;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}