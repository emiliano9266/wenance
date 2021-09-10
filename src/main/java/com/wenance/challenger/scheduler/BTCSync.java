package com.wenance.challenger.scheduler;

import com.wenance.challenger.providers.BTCProvider;
import com.wenance.challenger.repository.BTCPrice;
import com.wenance.challenger.services.BTCService;
import org.springframework.stereotype.Service;

@Service
public class BTCSync {
    private BTCProvider btcProvider;
    private BTCService btcService;

    public BTCSync(BTCProvider btcProvider, BTCService btcService) {
        this.btcProvider = btcProvider;
        this.btcService = btcService;
    }

    public BTCPrice sync() {
        return this.btcService.addPrice(btcProvider.getCurrentPrice());
    }
}
