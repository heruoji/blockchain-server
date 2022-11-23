package com.goalist.blockchainserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.sql.Timestamp;

@Data
public class Transaction implements Cloneable{
    private Date timestamp;
//    private Timestamp timestamp;
    private String senderBlockchainAddress;
    private String recipientBlockchainAddress;
    private double value;

//    public Transaction() {
//        this.timestamp = new Timestamp(System.currentTimeMillis());
//    }
    public Transaction() {
        this.timestamp = new Date();
    }

    public Transaction(String senderBlockchainAddress, String recipientBlockchainAddress, double value) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.senderBlockchainAddress = senderBlockchainAddress;
        this.recipientBlockchainAddress = recipientBlockchainAddress;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Transaction[timestamp=%s, senderBlockchainAddress=%s, recipientBlockchainAddress=%s, value=%s]", timestamp, senderBlockchainAddress, recipientBlockchainAddress, value);
    }

    @Override
    protected Transaction clone() throws CloneNotSupportedException {
        return (Transaction) super.clone();
    }
}
