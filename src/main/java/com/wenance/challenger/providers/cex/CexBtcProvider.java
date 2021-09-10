package com.wenance.challenger.providers.cex;

import com.wenance.challenger.providers.BTCProvider;
import com.wenance.challenger.repository.BTCPrice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Repository
class CexBtcProvider implements BTCProvider {

    private final RestTemplate restTemplate;

    public CexBtcProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BTCPrice getCurrentPrice() {
        ResponseEntity<BTCPriceCex> response = this.restTemplate.getForEntity("https://cex.io/api/last_price/BTC/USD", BTCPriceCex.class);
        return new BTCPrice(new BigDecimal(response.getBody().getLprice()));
    }
}
