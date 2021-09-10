package com.wenance.challenger.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BTCRepository {
    public BTCPrice save(BTCPrice btcPrice);

    public List<BTCPrice> findAll();

    public Optional<BTCPrice> findByDateTime(LocalDateTime dateTime);

    public List<BTCPrice> findBetweenDateTime(LocalDateTime from, LocalDateTime to);

    public void removeAll();
}
