package com.poc.event.sourcing.bank.entities;

import com.poc.event.sourcing.bank.command.CreateAccountCommand;
import com.poc.event.sourcing.bank.command.DepositMoneyCommand;
import com.poc.event.sourcing.bank.event.AccountCreatedEvent;
import com.poc.event.sourcing.bank.event.MoneyDepositedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.math.BigDecimal;

@Aggregate
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @AggregateIdentifier
    private String id;
    private String name;
    private BigDecimal amount;

    public AccountEntity() { }

    @CommandHandler
    public AccountEntity(CreateAccountCommand command) {
        String id = command.id;
        String name = command.accountCreator;

        Assert.hasLength(id, "Missing id");
        Assert.hasLength(name, "Missing account creator");

        AggregateLifecycle.apply(new AccountCreatedEvent(id, name, new BigDecimal(0)));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event) {
        this.id = event.id;
        this.name = event.accountCreator;
        this.amount = event.amount;
    }

    @CommandHandler
    protected void on(DepositMoneyCommand command) {
        BigDecimal amount = command.amount;
        Assert.isTrue(amount.doubleValue() > 0.0, "Deposit must be a positive number.");
        AggregateLifecycle.apply(new MoneyDepositedEvent(id, amount));
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event) {
       this.amount = new BigDecimal(0L);
       this.amount.add(event.amount);
    }
}

