package com.goalist.blockchainserver.model;

public class Miner {
    private String blockchainAddress;

    public Miner(String blockchainAddress) {
        this.blockchainAddress = blockchainAddress;
    }

    public String getBlockchainAddress() {
        return this.blockchainAddress;
    }
}
