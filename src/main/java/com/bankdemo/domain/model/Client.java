package com.bankdemo.domain.model;

public class Client {
    private int id;
    private String name;
    private String cedula;

    public Client(int id, String name, String cedula) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCedula() {
        return cedula;
    }
}
