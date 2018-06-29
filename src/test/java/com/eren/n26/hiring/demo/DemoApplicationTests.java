package com.eren.n26.hiring.demo;

import com.eren.n26.hiring.demo.controller.StatisticsController;
import com.eren.n26.hiring.demo.entity.Transaction;
import com.eren.n26.hiring.demo.service.MovingTransactionStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    MovingTransactionStatisticsService movingTransactionStatisticsService;

    @Test
    public void contextLoads() throws InterruptedException {
        StatisticsController c = new StatisticsController();

        for (int i = 0; i < 10; i++) {
            Transaction t = Transaction.builder().amount(Math.random()).timestamp(System.currentTimeMillis()).build();
            LOGGER.info("T:" + t);
            movingTransactionStatisticsService.add(t);
            LOGGER.info(""+movingTransactionStatisticsService.getStatistics());
            Thread.sleep(500L);
        }
    }

}
