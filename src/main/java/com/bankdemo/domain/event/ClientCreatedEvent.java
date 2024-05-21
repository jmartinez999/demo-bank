package com.bankdemo.domain.event;

public class ClientCreatedEvent {
    private final int clientId;
    private final String name;
    private final String cedula;

    public ClientCreatedEvent(int clientId, String name, String cedula) {
        this.clientId = clientId;
        this.name = name;
        this.cedula = cedula;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getCedula() {
        return cedula;
    }
}
