package com.poc.event.sourcing.bank.event;

import java.math.BigDecimal;

public class MoneyDepositedEvent extends BaseEvent<String> {

    public final BigDecimal amount;

    public MoneyDepositedEvent(String id, BigDecimal amount) {
        super(id);
        this.amount = amount;
    }
}
