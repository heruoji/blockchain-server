package com.goalist.blockchainserver.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionPool implements Cloneable{
    private List<Transaction> transactions;

    public TransactionPool() {
        this.transactions = new ArrayList<>();
    }

    public TransactionPool(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public void clear() {
        this.transactions.clear();
    }

    @Override
    public TransactionPool clone() throws CloneNotSupportedException {
        TransactionPool pool = new TransactionPool();
        for (Transaction transaction : transactions) {
            pool.addTransaction(transaction.clone());
        }
        return pool;
    }

    @Override
    public String toString() {
        return this.transactions.toString();
    }
}
