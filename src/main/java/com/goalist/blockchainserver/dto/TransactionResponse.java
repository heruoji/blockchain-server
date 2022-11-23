package com.goalist.blockchainserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Date timestamp;
//    private Timestamp timestamp;
    private String senderBlockchainAddress;
    private String recipientBlockchainAddress;
    private double value;
}
