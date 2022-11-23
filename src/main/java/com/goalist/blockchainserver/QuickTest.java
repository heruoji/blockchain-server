package com.goalist.blockchainserver;

import com.goalist.blockchainserver.model.*;
import com.goalist.blockchainserver.service.Blockchain;
import org.apache.tomcat.util.buf.HexUtils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class QuickTest {

    public static void main(String[] args) throws Exception {
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();
        Wallet walletM = new Wallet();

        String hexPublicKeyA = walletA.getHexStringPublicKey();
        String hexPublicKeyB = walletB.getHexStringPublicKey();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(HexUtils.fromHexString(hexPublicKeyA));
        KeyFactory kf = KeyFactory.getInstance("EC");
        PublicKey publicKey = kf.generatePublic(spec);

        String hexPublicKey2 = HexUtils.toHexString(publicKey.getEncoded());

        System.out.println(hexPublicKeyB);
        System.out.println(hexPublicKey2);
//        Miner miner = new Miner(walletM.getBlockchainAddress());
//        Blockchain blockchain = new Blockchain();
//
//        Transaction transaction = new Transaction();
//        transaction.setSenderBlockchainAddress(walletA.getBlockchainAddress());
//        transaction.setRecipientBlockchainAddress(walletB.getBlockchainAddress());
//        transaction.setValue(1.0);
//        String signature = walletA.generateSignature(transaction);
//
//        System.out.println(walletA.getPublicKey());
//
//        blockchain.addTransaction(transaction, walletA.getPublicKey(), signature);
//        System.out.println(blockchain.getTransactionPool());
//        System.out.println(blockchain.getChains());
//
//        blockchain.mining(miner.getBlockchainAddress());
//        System.out.println(blockchain.getTransactionPool());
//        System.out.println(blockchain.getChains());
    }
}
