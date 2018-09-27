package com.poc.event.sourcing.bank.command;

import java.math.BigDecimal;

public class DepositMoneyCommand extends BaseCommand<String> {

    public final BigDecimal amount;

    public DepositMoneyCommand(String id, BigDecimal amount) {
        super(id);
        this.amount = amount;
    }
}
