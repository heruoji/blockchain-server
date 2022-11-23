package com.goalist.blockchainserver.util;

import com.goalist.blockchainserver.dto.ChainResponse;
import com.goalist.blockchainserver.dto.TransactionPoolResponse;
import com.goalist.blockchainserver.dto.TransactionResponse;
import com.goalist.blockchainserver.entity.BlockData;
import com.goalist.blockchainserver.entity.TransactionData;
import com.goalist.blockchainserver.model.Block;
import com.goalist.blockchainserver.model.Transaction;
import com.goalist.blockchainserver.model.TransactionPool;
import org.apache.tomcat.util.buf.HexUtils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static List<Block> convertBlockFromEntity(List<BlockData> blockDataList) {
        List<Block> res = new ArrayList<>();
        for (BlockData blockData : blockDataList) {
            Block block = new Block();
            block.setTimestamp(blockData.getTimestamp());
            block.setPrevHash(blockData.getPrevHash());
            block.setNonce(blockData.getNonce());
            block.setTransactions(convertTransactionFromEntity(blockData.getTransactions()));
            res.add(block);
        }
        return res;
    }

    public static List<Transaction> convertTransactionFromEntity(List<TransactionData> transactionDataList) {
        List<Transaction> res = new ArrayList<>();
        for (TransactionData transactionData : transactionDataList) {
            Transaction transaction = new Transaction();
            transaction.setValue(transactionData.getValue());
            transaction.setSenderBlockchainAddress(transactionData.getSenderBlockchainAddress());
            transaction.setRecipientBlockchainAddress(transactionData.getRecipientBlockchainAddress());
            transaction.setTimestamp(transactionData.getTimestamp());
            res.add(transaction);
        }
        return res;
    }

    public static BlockData convertBlockToEntity(Block block) {
        BlockData blockData = new BlockData();
        blockData.setTimestamp(block.getTimestamp());
        blockData.setPrevHash(block.getPrevHash());
        blockData.setNonce(block.getNonce());
        blockData.setTransactions(convertTransactionToEntity(block.getTransactions()));
        return blockData;
    }

    public static List<TransactionData> convertTransactionToEntity(List<Transaction> transactions) {
        List<TransactionData> res = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionData transactionData = new TransactionData();
            transactionData.setTimestamp(transaction.getTimestamp());
            transactionData.setSenderBlockchainAddress(transaction.getSenderBlockchainAddress());
            transactionData.setRecipientBlockchainAddress(transaction.getRecipientBlockchainAddress());
            transactionData.setValue(transaction.getValue());
            res.add(transactionData);
        }
        return res;
    }

    public static TransactionPoolResponse convertTransactionPoolToResponse(TransactionPool transactionPool) {
        TransactionPoolResponse response = new TransactionPoolResponse();
        response.setNum(transactionPool.getTransactions().size());
        response.setTransactions(convertTransactionsToResponse(transactionPool.getTransactions()));
        return response;
    }

    public static List<ChainResponse> convertChainsToResponse(List<Block> chains) {
        List<ChainResponse> res = new ArrayList<>();
        for (Block block : chains) {
            ChainResponse chainResponse = new ChainResponse();
            chainResponse.setTimestamp(block.getTimestamp());
            chainResponse.setNonce(block.getNonce());
            chainResponse.setPrevHash(block.getPrevHash());
            chainResponse.setTransactions(convertTransactionsToResponse(block.getTransactions()));
            res.add(chainResponse);
        }
        return res;
    }

    public static List<TransactionResponse> convertTransactionsToResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTimestamp(transaction.getTimestamp());
            transactionResponse.setRecipientBlockchainAddress(transaction.getRecipientBlockchainAddress());
            transactionResponse.setSenderBlockchainAddress(transaction.getSenderBlockchainAddress());
            transactionResponse.setValue(transaction.getValue());
            transactionResponses.add(transactionResponse);
        }
        return transactionResponses;
    }

    public static PublicKey convertPublicKeyFromHexString(String hexString) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(HexUtils.fromHexString(hexString));
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePublic(spec);
    }

    public static PrivateKey convertPrivateKeyFromHexString(String hexString) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(HexUtils.fromHexString(hexString));
        KeyFactory kf = KeyFactory.getInstance("EC");
        return kf.generatePrivate(spec);
    }
}
