package com.wenance.challenger.services;

import com.wenance.challenger.repository.BTCPrice;
import com.wenance.challenger.repository.BTCRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class BTCServiceImpl implements BTCService {

    private final BTCRepository btcRepository;

    public BTCServiceImpl(BTCRepository btcRepository) {
        this.btcRepository = btcRepository;
    }

    @Override
    public List<BTCPrice> findAll() {
        return this.btcRepository.findAll();
    }

    @Override
    public BTCPrice addPrice(BTCPrice btc) {
        return this.btcRepository.save(btc);
    }

    @Override
    public BTCPrice findByDateTime(LocalDateTime dateTime) {
        return this.btcRepository.findByDateTime(dateTime).orElseThrow(() -> new ResourceNotFoundException("Price not found"));
    }

    @Override
    public List<BTCPrice> findBetweenDateTime(LocalDateTime from, LocalDateTime to) {
        return this.btcRepository.findBetweenDateTime(from, to);
    }

    @Override
    public BTCAverage getAverage(LocalDateTime from, LocalDateTime to) {
        List<BTCPrice> list = this.findBetweenDateTime(from, to);

        BTCPrice max = this.findAll().stream().max(Comparator.comparing(BTCPrice::getPrice)).orElseThrow(() -> new ResourceNotFoundException("Prices range is empty"));
        BigDecimal average = list.stream().map(BTCPrice::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_EVEN);
        BigDecimal percentage = average.subtract(max.getPrice()).divide(max.getPrice(), 5, RoundingMode.HALF_EVEN).multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);

        return BTCAverage.builder()
                .percentage(percentage)
                .average(average)
                .max(max.getPrice())
                .totalElements(list.size())
                .build();
    }
}
