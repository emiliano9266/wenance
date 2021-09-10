package com.wenance.challenger.services;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class BTCAverage {
    private Integer totalElements;
    private BigDecimal max;
    private BigDecimal average;
    private BigDecimal percentage;
}
