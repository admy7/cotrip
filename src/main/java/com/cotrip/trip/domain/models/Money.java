package com.cotrip.trip.domain.models;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class Money {
    private BigDecimal amount;

    private Currency currency;

    public Money(double amount, Currency currency) {
        this.amount = BigDecimal.valueOf(amount);
        this.currency = currency;
    }

    public Money() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
