package com.goalist.blockchainserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostTransactionPoolRequest {
    private String senderBlockchainAddress;
    private String recipientBlockchainAddress;
    private double value;
    private String signature;
    private String publicKey;
}
