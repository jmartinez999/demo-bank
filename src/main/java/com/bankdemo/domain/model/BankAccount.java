package com.bankdemo.domain.model;

import com.bankdemo.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private int id;
    private int clientId;
    private List<DomainEvent> events = new ArrayList<>();

    public BankAccount(int id, int clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public List<DomainEvent> getEvents() {
        return events;
    }

    public void addEvent(DomainEvent event) {
        events.add(event);
    }
}
