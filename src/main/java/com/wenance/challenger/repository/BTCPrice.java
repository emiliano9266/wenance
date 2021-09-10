package com.wenance.challenger.repository;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BTCPrice {
    private BigDecimal price;
    private LocalDateTime dateTime;

    public BTCPrice(BigDecimal price) {
        this.price = price;
        this.dateTime = LocalDateTime.now();
    }
}
