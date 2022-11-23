package com.goalist.blockchainserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.sql.Timestamp;

@Data
public class TransactionData {
    private Date timestamp;
//    private Timestamp timestamp;
    private String senderBlockchainAddress;
    private String recipientBlockchainAddress;
    private double value;
}
