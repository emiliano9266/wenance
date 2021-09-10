package com.wenance.challenger.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
class BTCRepositoryImpl implements BTCRepository {

    private final List<BTCPrice> historicalPrices = new ArrayList<>();

    @Override
    public BTCPrice save(BTCPrice btcPrice) {
        this.historicalPrices.add(btcPrice);
        return btcPrice;
    }

    @Override
    public List<BTCPrice> findAll() {
        return this.historicalPrices;
    }

    @Override
    public Optional<BTCPrice> findByDateTime(LocalDateTime dateTime) {
        return this.historicalPrices.stream()
                .filter(btc -> btc.getDateTime().isEqual(dateTime))
                .findAny();
    }

    @Override
    public List<BTCPrice> findBetweenDateTime(LocalDateTime from, LocalDateTime to) {
        return this.historicalPrices.stream()
                .filter(btc -> (btc.getDateTime().equals(from) || btc.getDateTime().isAfter(from)) && (btc.getDateTime().equals(to) || btc.getDateTime().isBefore(to)))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAll() {
        this.historicalPrices.removeAll(this.historicalPrices);
    }
}
