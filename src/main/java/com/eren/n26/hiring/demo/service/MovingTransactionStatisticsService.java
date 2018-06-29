package com.eren.n26.hiring.demo.service;

import com.eren.n26.hiring.demo.entity.Statistics;
import com.eren.n26.hiring.demo.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
public class MovingTransactionStatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovingTransactionStatisticsService.class);

    private static List<Transaction> transactions = new Vector<Transaction>();
    private static Double sum = 0d;
    private static Statistics statistics;

    private static int SECOND_FILTER = 60000; // only keep 60 second


    public void add(Transaction transaction) {
        sum = sum + transaction.getAmount();
        transactions.add(transaction);
        transactions =
                transactions.stream()
                        .filter(p -> p.getTimestamp() >= System.currentTimeMillis() - SECOND_FILTER)  // only keep 60 second
                        .sorted(Comparator.comparingLong(Transaction::getTimestamp)) // sort by mount
                        .collect(Collectors.toList());

        transactions.sort(Comparator.comparingDouble(Transaction::getAmount));

        statistics = statistics.builder().sum(getSum()).
                avg(getAverage()).
                count(getCount()).
                max(getMax()).
                min(getMin()).build();

        LOGGER.info(""+transactions);
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

    public static Statistics getStatistics() {
        return statistics;
    }

    /*@Scheduled(fixedDelay = 10000)
    public void manageData() {
        LOGGER.info("Filtering and sorting transaction");
        transactions =
                transactions.stream()
                        .filter(p -> p.getTimestamp() >= System.currentTimeMillis() - 60000)  // only keep 60 second
                        .sorted(Comparator.comparingDouble(Transaction::getAmount)) // sort by mount
                        .collect(Collectors.toList());

        transactions.sort(Comparator.comparingLong(Transaction::getTimestamp));

        statistics = statistics.builder().sum(getSum()).
                        avg(getAverage()).
                        count(getCount()).
                        max(getMax()).
                        min(getMin()).build();
    }*/

}