package com.goalist.blockchainserver.model;

import com.goalist.blockchainserver.util.Sha256Algorithm;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Block {
//    private Timestamp timestamp;
    private Date timestamp;
    private List<Transaction> transactions;
    private int nonce;
    private String prevHash;

    public Block(List<Transaction> transactions, int nonce, String prevHash) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.transactions = transactions;
        this.nonce = nonce;
        this.prevHash = prevHash;
    }

    public String hash() {
        return Sha256Algorithm.sha256(transactions.toString() + nonce + prevHash);
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    @Override
    public String toString() {
        return String.format("Block[timestamp=%s, transactions=%s, nonce=%s, prevHash=%s]", timestamp, transactions, nonce, prevHash);
    }
}
