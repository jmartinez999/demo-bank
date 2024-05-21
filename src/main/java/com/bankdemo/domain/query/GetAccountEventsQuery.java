package com.bankdemo.domain.query;

public class GetAccountEventsQuery {
    private final int accountId;

    public GetAccountEventsQuery(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }
}
