package com.poc.event.sourcing.bank.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class DepositDto {

    @NotNull(message = "Amount can't be empty")
    @Min(value = 1, message = "The value to deposit can't be minor then 1")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
