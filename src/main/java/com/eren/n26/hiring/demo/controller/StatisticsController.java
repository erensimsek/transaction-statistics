package com.eren.n26.hiring.demo.controller;

import com.eren.n26.hiring.demo.entity.Statistics;
import com.eren.n26.hiring.demo.entity.Transaction;
import com.eren.n26.hiring.demo.service.MovingTransactionStatisticsService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Api(value = "API is to\n" +
        "calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is\n" +
        "called every time a transaction is made. It is also the sole input of this rest API. The other one\n" +
        "returns the statistic based of the transactions of the last 60 seconds.")
@Component
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    MovingTransactionStatisticsService movingTransactionStatisticsService;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createTransaction(@RequestBody Transaction transactionRequest) {

        LOGGER.info("Creating new transaction: "+transactionRequest);

        if(transactionRequest.getTimestamp() - System.currentTimeMillis() > 60000) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        movingTransactionStatisticsService.add(transactionRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Statistics getStatistics() {
        return MovingTransactionStatisticsService.getStatistics();
    }

}
