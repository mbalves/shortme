package com.mbalves.shortme.domain;

import java.util.Date;

public class Statistics {

    private Date startDate;
    private Date lastChange;
    private Long quantity;
    private Long quantityLastDay;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityLastDay() {
        return quantityLastDay;
    }

    public void setQuantityLastDay(Long quantityLastDay) {
        this.quantityLastDay = quantityLastDay;
    }
}
