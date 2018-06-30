package com.eren.n26.hiring.demo;

import static org.assertj.core.api.BDDAssertions.then;

import com.eren.n26.hiring.demo.pojo.Transaction;
import com.eren.n26.hiring.demo.exception.TooOlderTransactionException;
import com.eren.n26.hiring.demo.service.MovingTransactionStatisticsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionStatisticsApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionStatisticsApplicationTests.class);

    @Autowired
    private MovingTransactionStatisticsService movingTransactionStatisticsService;

    @LocalServerPort
    private int port;

    @Test
    public void statisticsServiceServiceTest() throws InterruptedException {

        LOGGER.info("Testing statisticsServiceServiceTest() method is Starting...");

        for (int i = 0; i < 20; i++) {
            Transaction transaction = Transaction.builder().amount(Double.parseDouble(""+i)).timestamp(System.currentTimeMillis()).build();
            try {
                movingTransactionStatisticsService.add(transaction);
            } catch (TooOlderTransactionException e) {
                LOGGER.debug(e.getMessage());
            }
            Thread.sleep(1000L);
        }

        Assert.assertTrue("Avg must be 14.5",movingTransactionStatisticsService.getStatistics().getAvg()==14.5d);
        Assert.assertTrue("Sum must be 145",movingTransactionStatisticsService.getStatistics().getSum()==145.0d);
        Assert.assertTrue("Count must be 10",movingTransactionStatisticsService.getStatistics().getCount()==10.0d);
        Assert.assertTrue("Min must be 10.0",movingTransactionStatisticsService.getStatistics().getMin()==10.0d);
        Assert.assertTrue("Max must be 19.0",movingTransactionStatisticsService.getStatistics().getMax()==19.0d);

        LOGGER.info(movingTransactionStatisticsService.getStatistics().toString());

        LOGGER.info("Testing statisticsServiceServiceTest() method completed");
    }

}
