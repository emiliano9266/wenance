package com.wenance.challenger.services;

import com.wenance.challenger.repository.BTCPrice;
import com.wenance.challenger.repository.BTCRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BTCServiceImplTest {

    @MockBean
    BTCRepository btcRepository;

    @Autowired
    BTCService btcService;

    @Test
    void findAll() {
        this.btcService.findAll();
        Mockito.verify(btcRepository).findAll();
    }

    @Test
    void addPrice() {
        Mockito.when(btcRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArguments()[0]);
        BTCPrice btcPrice = this.btcService.addPrice(new BTCPrice(BigDecimal.TEN));
        assertEquals(BigDecimal.TEN, btcPrice.getPrice());
        Mockito.verify(btcRepository).save(btcPrice);
    }

    @Test
    void findByDateTime() {
        LocalDateTime withPrice = LocalDateTime.now();
        LocalDateTime withOutPrice = LocalDateTime.now();
        Mockito.when(btcRepository.findByDateTime(withPrice)).thenReturn(Optional.of(new BTCPrice(BigDecimal.TEN)));
        Mockito.when(btcRepository.findByDateTime(withOutPrice)).thenReturn(Optional.empty());

        assertNotNull(this.btcService.findByDateTime(withPrice));
        assertThrows(ResourceNotFoundException.class, () -> {
            this.btcService.findByDateTime(withOutPrice);
        });
        Mockito.verify(btcRepository, Mockito.times(2)).findByDateTime(Mockito.any());
    }

    @Test
    void findBetweenDateTime() {
        this.btcService.findBetweenDateTime(LocalDateTime.now(), LocalDateTime.now());
        Mockito.verify(btcRepository).findBetweenDateTime(Mockito.any(), Mockito.any());
    }

    @Test
    void getAverage() {
        List<BTCPrice> list1 = new ArrayList();
        list1.add(new BTCPrice(new BigDecimal(10)));
        list1.add(new BTCPrice(new BigDecimal(20)));
        list1.add(new BTCPrice(new BigDecimal(30)));

        List<BTCPrice> list2 = new ArrayList<>(list1);
        list2.add(new BTCPrice(new BigDecimal(40)));

        Mockito.when(btcRepository.findBetweenDateTime(Mockito.any(), Mockito.any())).thenReturn(list1);
        Mockito.when(btcRepository.findAll()).thenReturn(list2);

        BTCAverage average = this.btcService.getAverage(LocalDateTime.now(), LocalDateTime.now());

        assertEquals(new BigDecimal("20.00"), average.getAverage());
        assertEquals(new BigDecimal(40), average.getMax());
        assertEquals(3, average.getTotalElements());
        assertEquals(new BigDecimal("-50.00"), average.getPercentage());

        Mockito.when(btcRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> {
            this.btcService.getAverage(LocalDateTime.now(), LocalDateTime.now());
        });
    }
}