package com.poc.event.sourcing.bank.api.dto;

import javax.validation.constraints.NotNull;

public class AccountDto {

    @NotNull(message = "Name can't be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
