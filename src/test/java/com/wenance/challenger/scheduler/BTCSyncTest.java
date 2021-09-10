package com.wenance.challenger.scheduler;

import com.wenance.challenger.providers.BTCProvider;
import com.wenance.challenger.repository.BTCRepository;
import com.wenance.challenger.services.BTCServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MockBean(BTCScheduler.class)
class BTCSyncTest {

    @Autowired
    private BTCRepository btcRepository;

    @Autowired
    private BTCProvider btcProvider;

    @Autowired
    private BTCServiceImpl btcService;

    @Autowired
    private BTCSync btcSync;

    @BeforeAll
    void config() {
        btcRepository.removeAll();
    }

    @Test
    void sync() {
        this.btcSync.sync();
        assertEquals(1, this.btcRepository.findAll().size());
    }
}