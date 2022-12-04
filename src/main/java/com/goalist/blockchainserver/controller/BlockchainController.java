package com.goalist.blockchainserver.controller;

import com.goalist.blockchainserver.dto.*;
import com.goalist.blockchainserver.model.Transaction;
import com.goalist.blockchainserver.model.Wallet;
import com.goalist.blockchainserver.service.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import static com.goalist.blockchainserver.util.Converter.*;

@RestController
@CrossOrigin
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

    @GetMapping("/deposits")
    public ResponseEntity<GetAmountResponse> getDeposits(@RequestParam("address") String blockchainAddress) {
        double totalAmount = blockchain.calculateTotalAmount(blockchainAddress);
        GetAmountResponse response = new GetAmountResponse();
        response.setTotalAmount(totalAmount);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    TODO WalletサーバーとBlockchainサーバーを分離
    @PostMapping("/transaction-pool")
    public ResponseEntity<Void> postTransaction(@RequestBody PostTransactionPoolRequest request) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setSenderBlockchainAddress(request.getSenderBlockchainAddress());
        transaction.setRecipientBlockchainAddress(request.getRecipientBlockchainAddress());
        transaction.setValue(request.getValue());
        PrivateKey privateKey = convertPrivateKeyFromHexString(request.getPrivateKey());
        PublicKey publicKey = convertPublicKeyFromHexString(request.getPublicKey());
        Wallet senderWallet = new Wallet(privateKey, publicKey, request.getSenderBlockchainAddress());
        if(!senderWallet.confirmAddress(request.getSenderBlockchainAddress())){
            throw new Exception("情報が不正です");
        };

        blockchain.addTransaction(transaction, senderWallet.getPublicKey(), senderWallet.generateSignature(transaction));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/wallet")
    public ResponseEntity<GetWalletResponse> getWallet() throws NoSuchAlgorithmException {
        Wallet wallet = new Wallet();
        GetWalletResponse response = new GetWalletResponse();
        response.setPrivateKey(wallet.getHexStringPrivateKey());
        response.setPublicKey(wallet.getHexStringPublicKey());
        response.setBlockchainAddress(wallet.getBlockchainAddress());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
