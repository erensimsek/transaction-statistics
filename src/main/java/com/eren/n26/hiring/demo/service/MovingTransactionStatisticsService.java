package com.eren.n26.hiring.demo.service;

import com.eren.n26.hiring.demo.pojo.Statistics;
import com.eren.n26.hiring.demo.pojo.Transaction;
import com.eren.n26.hiring.demo.exception.TooOlderTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summarizingDouble;

@Service
public class MovingTransactionStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingTransactionStatisticsService.class);

    private List<Transaction> transactions = new Vector<Transaction>();
    private Statistics statistics;

    @Value("${second_filter_milisecond}")
    private int SECOND_FILTER; // only keep SECOND_FILTER second

    public void add(Transaction transaction) throws TooOlderTransactionException {
        if (System.currentTimeMillis() - transaction.getTimestamp() < SECOND_FILTER) {
            transactions.add(transaction);
            collectStatistics();
        } else {
            throw new TooOlderTransactionException("Timestamp of transaction is too older.");
        }
    }

    @Scheduled(fixedDelay = 1000)
    private void collectStatistics() {

        transactions = transactions.stream().
                filter(p -> (System.currentTimeMillis() - p.getTimestamp()) < SECOND_FILTER)// only keep SECOND_FILTER second
                .collect(Collectors.toList());

        DoubleSummaryStatistics result = transactions.stream()
                .collect(summarizingDouble(Transaction::getAmount));

        statistics = statistics.builder().sum(result.getSum()).
                avg(result.getAverage()).
                count(result.getCount()).
                max(transactions.isEmpty()?0d:result.getMax()).
                min(transactions.isEmpty()?0d:result.getMin()).build();

    }

    public Statistics getStatistics() {
        return statistics;
    }

}