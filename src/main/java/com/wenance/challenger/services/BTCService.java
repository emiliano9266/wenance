package com.wenance.challenger.services;

import com.wenance.challenger.repository.BTCPrice;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface BTCService {
    public List<BTCPrice> findAll();

    public BTCPrice addPrice(BTCPrice btc);

    public BTCPrice findByDateTime(LocalDateTime dateTime) throws ResourceNotFoundException;

    public List<BTCPrice> findBetweenDateTime(LocalDateTime from, LocalDateTime to);

    public BTCAverage getAverage(LocalDateTime from, LocalDateTime to) throws ResourceNotFoundException;
}
