package com.goalist.blockchainserver.dto;

import com.goalist.blockchainserver.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChainResponse {
    private Date timestamp;
//    private Timestamp timestamp;
    private List<TransactionResponse> transactions;
    private int nonce;
    private String prevHash;
}
