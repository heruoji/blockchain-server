package com.goalist.blockchainserver.entity;

import com.goalist.blockchainserver.model.Transaction;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class BlockData {
    @Id
    private String id;
//    private Timestamp timestamp;
    private Date timestamp;
    private List<TransactionData> transactions;
    private int nonce;
    private String prevHash;
}
