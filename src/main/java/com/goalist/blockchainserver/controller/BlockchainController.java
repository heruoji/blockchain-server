package com.goalist.blockchainserver.controller;

import com.goalist.blockchainserver.dto.*;
import com.goalist.blockchainserver.model.Transaction;
import com.goalist.blockchainserver.service.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.goalist.blockchainserver.util.Converter.*;

@RestController
public class BlockchainController {

    @Autowired
    Blockchain blockchain;

    @GetMapping("/chains")
    public ResponseEntity<List<ChainResponse>> getChains() {
        List<ChainResponse> chainResponses = convertChainsToResponse(blockchain.getChains());
        return new ResponseEntity<>(chainResponses, HttpStatus.OK);
    }

    @PostMapping("/mining")
    public ResponseEntity<Void> mining(@RequestBody MiningRequest request) throws Exception {
        blockchain.mining(request.getMinerBlockchainAddress());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/transaction-pool")
    public ResponseEntity<TransactionPoolResponse> getTransactionPool() {
        TransactionPoolResponse response = convertTransactionPoolToResponse(this.blockchain.getTransactionPool());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/transaction-pool")
    public ResponseEntity<Void> postTransaction(@RequestBody PostTransactionPoolRequest request) {
        Transaction transaction = new Transaction();
        transaction.setSenderBlockchainAddress(request.getSenderBlockchainAddress());
        transaction.setRecipientBlockchainAddress(request.getRecipientBlockchainAddress());
        transaction.setValue(request.getValue());
//        blockchain.addTransaction(transaction, request.getPublicKey(), request.getSignature());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
