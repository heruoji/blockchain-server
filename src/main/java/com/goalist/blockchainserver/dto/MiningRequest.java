package com.goalist.blockchainserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiningRequest {
    private String minerBlockchainAddress;
}
