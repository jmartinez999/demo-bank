package com.bankdemo.domain.command;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateClientCommand {
    private final String name;
    private final String cedula;

    @JsonCreator
    public CreateClientCommand(@JsonProperty("name") String name, @JsonProperty("cedula") String cedula) {
        this.name = name;
        this.cedula = cedula;
    }

    public String getName() {
        return name;
    }

    public String getCedula() {
        return cedula;
    }
}
