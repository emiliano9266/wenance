package com.wenance.challenger.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BTCScheduler {

    private final BTCSync btcSync;

    public BTCScheduler(BTCSync btcSync) {
        this.btcSync = btcSync;
    }

    @Scheduled(fixedDelayString = "${wenance.check-price}")
    public void findBTCCurrentPrice() {
        this.btcSync.sync();
    }
}
