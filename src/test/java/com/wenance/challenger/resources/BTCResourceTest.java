package com.wenance.challenger.resources;

import com.wenance.challenger.repository.BTCPrice;
import com.wenance.challenger.services.BTCAverage;
import com.wenance.challenger.services.BTCService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BTCResource.class)
class BTCResourceTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private BTCService btcService;

    @Test
    void findAll() throws Exception {
        List<BTCPrice> list = new ArrayList<>();
        list.add(new BTCPrice(BigDecimal.TEN));
        list.add(new BTCPrice(new BigDecimal("25")));
        Mockito.when(btcService.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/btc").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(list.size()))
                .andExpect(jsonPath("$[0].price").value(list.get(0).getPrice().toString()))
                .andExpect(jsonPath("$[1].price").value(list.get(1).getPrice().toString()));

        Mockito.verify(this.btcService).findAll();
    }

    @Test
    void findByDateTimeOk() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        BTCPrice test = new BTCPrice(new BigDecimal("25"));
        Mockito.when(btcService.findByDateTime(now)).thenReturn(test);

        this.mockMvc.perform(get("/btc/?dateTime=" + now).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value("25"))
                .andExpect(jsonPath("$.dateTime").value(test.getDateTime().toString()));

        Mockito.verify(this.btcService).findByDateTime(now);
    }

    @Test
    void findByDateTimeNotFound() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Mockito.when(btcService.findByDateTime(now)).thenThrow(new ResourceNotFoundException("Price not found"));

        this.mockMvc.perform(get("/btc/?dateTime=" + now).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages[0]").value("Price not found"));

        Mockito.verify(this.btcService).findByDateTime(now);
    }

    @Test
    void average() throws Exception {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now();
        Mockito.when(btcService.getAverage(from, to)).thenReturn(BTCAverage.builder()
                .percentage(new BigDecimal("-2"))
                .average(new BigDecimal("20"))
                .max(new BigDecimal("20.4"))
                .totalElements(2)
                .build());

        this.mockMvc.perform(get("/btc/average?from=" + from + "&to=" + to).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.percentage").value("-2"))
                .andExpect(jsonPath("$.average").value("20"))
                .andExpect(jsonPath("$.max").value("20.4"))
                .andExpect(jsonPath("$.totalElements").value("2"));
    }

    @Test
    void averageWhenRangeIsEmpty() throws Exception {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = LocalDateTime.now();
        Mockito.when(btcService.getAverage(from, to)).thenThrow(new ResourceNotFoundException("Prices range is empty"));

        this.mockMvc.perform(get("/btc/average?from=" + from + "&to=" + to).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messages").isArray())
                .andExpect(jsonPath("$.messages[0]").value("Prices range is empty"));

        Mockito.verify(this.btcService).getAverage(from, to);
    }
}