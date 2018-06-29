package com.eren.n26.hiring.demo.entity;


public class Statistics {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private double count;

    public Statistics(double sum, double avg, double max, double min, double count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public static StatisticsBuilder builder() {
        return new StatisticsBuilder();
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }

    public static class StatisticsBuilder {
        private double sum;
        private double avg;
        private double max;
        private double min;
        private double count;

        StatisticsBuilder() {
        }

        public StatisticsBuilder sum(double sum) {
            this.sum = sum;
            return this;
        }

        public StatisticsBuilder avg(double avg) {
            this.avg = avg;
            return this;
        }

        public StatisticsBuilder max(double max) {
            this.max = max;
            return this;
        }

        public StatisticsBuilder min(double min) {
            this.min = min;
            return this;
        }

        public StatisticsBuilder count(double count) {
            this.count = count;
            return this;
        }

        public Statistics build() {
            return new Statistics(sum, avg, max, min, count);
        }

        public String toString() {
            return "Statistics.StatisticsBuilder(sum=" + this.sum + ", avg=" + this.avg + ", max=" + this.max + ", min=" + this.min + ", count=" + this.count + ")";
        }
    }
}
