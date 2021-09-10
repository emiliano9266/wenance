package com.wenance.challenger.providers;

import com.wenance.challenger.repository.BTCPrice;

public interface BTCProvider {
    public BTCPrice getCurrentPrice();
}
