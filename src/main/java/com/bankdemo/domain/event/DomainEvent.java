package com.bankdemo.domain.event;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;

public abstract class DomainEvent {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date occurredOn = new Date();

    public Date getOccurredOn() {
        return occurredOn;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public String getEventType() {
        return this.getClass().getSimpleName();
    }
}
