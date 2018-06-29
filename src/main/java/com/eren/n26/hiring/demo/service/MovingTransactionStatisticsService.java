package com.eren.n26.hiring.demo.service;

import com.eren.n26.hiring.demo.entity.Statistics;
import com.eren.n26.hiring.demo.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
public class MovingTransactionStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingTransactionStatisticsService.class);

    private List<Transaction> transactions = new Vector<Transaction>();
    private Double sum;
    private Statistics statistics;

    @Value("${second_filter_milisecond}")
    private int SECOND_FILTER; // only keep SECOND_FILTER second

    public void add(Transaction transaction) {
        if (System.currentTimeMillis() - transaction.getTimestamp() < SECOND_FILTER) {
            transactions.add(transaction);
            collectStatistics();
        }
    }

    @Scheduled(fixedDelay = 1000)
    private void collectStatistics() {
        transactions =
                transactions.stream()
                        .filter(p -> (System.currentTimeMillis() - p.getTimestamp()) < SECOND_FILTER)  // only keep SECOND_FILTER second
                        .sorted(Comparator.comparingDouble(Transaction::getAmount))
                        .collect(Collectors.toList());

        sum = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        statistics = statistics.builder().sum(getSum()).
                avg(getAverage()).
                count(getCount()).
                max(getMax()).
                min(getMin()).build();

        LOGGER.info("Transaction "+statistics +" in last " + SECOND_FILTER/1000 +" seconds");
    }

    public Double getAverage() {
        if (transactions.isEmpty()) return 0d;
        return sum / transactions.size();
    }

    public double getCount() {
        if (transactions.isEmpty()) return 0d;
        return transactions.size();
    }

    public double getMax() {
        if (transactions.isEmpty()) return 0d;
        return transactions.get(transactions.size() - 1).getAmount();
    }

    public double getMin() {
        if (transactions.isEmpty()) return 0d;
        return transactions.get(0).getAmount();
    }

    public double getSum() {
        if (transactions.isEmpty()) return 0d;
        return sum;
    }

    public Statistics getStatistics() {
        return statistics;
    }

}