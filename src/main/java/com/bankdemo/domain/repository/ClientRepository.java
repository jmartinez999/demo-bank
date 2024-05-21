package com.bankdemo.domain.repository;

import com.bankdemo.domain.model.Client;

public interface ClientRepository {
    int createClient(String name, String cedula);
    Client getClientByCedula(String cedula);
    Client getClientById(int clientId);
}
