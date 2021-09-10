package com.wenance.challenger.resources;

import com.wenance.challenger.services.BTCAverage;
import com.wenance.challenger.repository.BTCPrice;
import com.wenance.challenger.services.BTCService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/btc")
public class BTCResource {

    private final BTCService btcService;

    public BTCResource(BTCService btcService) {
        this.btcService = btcService;
    }

    @GetMapping
    @Operation(summary = "Find all")
    public ResponseEntity<List<BTCPrice>> findAll() {
        return ResponseEntity.ok(this.btcService.findAll());
    }

    @GetMapping("/")
    @Operation(summary = "Find by timestamp")
    public ResponseEntity<BTCPrice> findByDateTime(
            @RequestParam(value = "dateTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime
    ) {
        return ResponseEntity.ok(this.btcService.findByDateTime(dateTime));
    }

    @GetMapping("/average")
    @Operation(summary = "Get average")
    public ResponseEntity<BTCAverage> average(
            @RequestParam(value = "from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(this.btcService.getAverage(from, to));
    }
}
