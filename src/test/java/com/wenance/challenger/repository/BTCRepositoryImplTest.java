package com.wenance.challenger.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BTCRepositoryImplTest {

    @Autowired
    private BTCRepositoryImpl btcRepository;

    @AfterEach
    void cleanRepository() {
        this.btcRepository.removeAll();
    }

    @Test
    void save() {
        BTCPrice btcPrice = new BTCPrice(new BigDecimal(25));
        this.btcRepository.save(btcPrice);
        List<BTCPrice> list = this.btcRepository.findAll();
        assertEquals(1, list.size());
        assertEquals(list.get(0).getPrice(), btcPrice.getPrice());
        assertEquals(list.get(0).getDateTime(), btcPrice.getDateTime());
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        assertEquals(2, list.size());
        assertEquals(list.get(1).getPrice(), new BigDecimal(30));
        assertTrue(list.get(1).getDateTime().isAfter(list.get(0).getDateTime()));
    }

    @Test
    void findAll() {
        assertEquals(this.btcRepository.findAll().size(), 0);
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        assertEquals(this.btcRepository.findAll().size(), 5);
    }

    @Test
    void findByDateTime() {
        BTCPrice btcPrice = new BTCPrice(new BigDecimal(25));
        this.btcRepository.save(btcPrice);
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        Optional<BTCPrice> price = this.btcRepository.findByDateTime(btcPrice.getDateTime());
        assertTrue(price.isPresent());
        assertEquals(new BigDecimal(25), price.get().getPrice());
    }

    @Test
    void findBetweenDateTime() {
        BTCPrice fromBtcPrice = new BTCPrice(new BigDecimal(25));
        this.btcRepository.save(fromBtcPrice);
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(20)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(15)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(35)));
        BTCPrice toBtcPrice = new BTCPrice(new BigDecimal(25));
        this.btcRepository.save(toBtcPrice);
        this.btcRepository.save(new BTCPrice(new BigDecimal(50)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(55)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(45)));

        List<BTCPrice> list = this.btcRepository.findBetweenDateTime(fromBtcPrice.getDateTime(), toBtcPrice.getDateTime());
        assertEquals(6, list.size());
        list.forEach(btcPrice -> assertTrue(
                (
                        btcPrice.getDateTime().equals(fromBtcPrice.getDateTime()) ||
                                btcPrice.getDateTime().isAfter(fromBtcPrice.getDateTime())
                ) &&
                        (
                                btcPrice.getDateTime().equals(toBtcPrice.getDateTime()) ||
                                        btcPrice.getDateTime().isBefore(toBtcPrice.getDateTime())
                        )
        ));
    }

    @Test
    void removeAll() {
        this.btcRepository.save(new BTCPrice(new BigDecimal(30)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(20)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(15)));
        this.btcRepository.save(new BTCPrice(new BigDecimal(35)));
        assertEquals(4, this.btcRepository.findAll().size());
        this.btcRepository.removeAll();
        assertEquals(0, this.btcRepository.findAll().size());
    }
}