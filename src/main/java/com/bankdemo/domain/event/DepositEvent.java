package com.bankdemo.domain.event;

public class DepositEvent extends DomainEvent{
    private final double amount;

    public DepositEvent(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
