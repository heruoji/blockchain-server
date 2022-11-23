package com.goalist.blockchainserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class TransactionPoolData {
    @Id
    private String id;

    private List<TransactionData> transactions;
}
