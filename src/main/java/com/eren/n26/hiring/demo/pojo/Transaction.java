package com.eren.n26.hiring.demo.pojo;


import javax.validation.constraints.NotNull;

public class Transaction {

    @NotNull
    private Double amount;

    @NotNull
    private Long timestamp;

    public Transaction() {
    }

    public Transaction(@NotNull Double amount, @NotNull Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static class TransactionBuilder {
        private @NotNull Double amount;
        private @NotNull Long timestamp;

        public TransactionBuilder() {
        }

        public TransactionBuilder amount(@NotNull Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder timestamp(@NotNull Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Transaction build() {
            return new Transaction(amount, timestamp);
        }

        public String toString() {
            return "Transaction.TransactionBuilder(amount=" + this.amount + ", timestamp=" + this.timestamp + ")";
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
