package com.bankdemo.domain.event;

public class WithdrawEvent extends DomainEvent{
    private final double amount;

    public WithdrawEvent(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
