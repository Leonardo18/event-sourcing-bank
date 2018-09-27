package com.poc.event.sourcing.bank.event;

import java.math.BigDecimal;

public class AccountCreatedEvent  extends BaseEvent<String> {

    public final String accountCreator;
    public final BigDecimal amount;

    public AccountCreatedEvent(String id, String accountCreator, BigDecimal amount) {
        super(id);
        this.accountCreator = accountCreator;
        this.amount = amount;
    }
}
