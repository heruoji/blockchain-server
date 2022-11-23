package com.goalist.blockchainserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPoolResponse {
    private int num;
    private List<TransactionResponse> transactions;
}
