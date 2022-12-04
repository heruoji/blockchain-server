package com.goalist.blockchainserver.service;

import com.goalist.blockchainserver.entity.BlockData;
import com.goalist.blockchainserver.entity.TransactionData;
import com.goalist.blockchainserver.entity.TransactionPoolData;
import com.goalist.blockchainserver.model.Block;
import com.goalist.blockchainserver.model.Transaction;
import com.goalist.blockchainserver.model.TransactionPool;
import com.goalist.blockchainserver.repository.BlockDataRepository;
import com.goalist.blockchainserver.repository.BlockchainDataRepository;
import com.goalist.blockchainserver.repository.TransactionPoolRepository;
import com.goalist.blockchainserver.util.Sha256Algorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.DatatypeConverter;
import java.security.PublicKey;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

@Data
public class Blockchain {
    private TransactionPool transactionPool;
    private List<Block> chains;
    private String MINING_SENDER_ADDRESS = "THE BLOCKCHAIN";
    private double MINING_REWARD = 1.0;
    private int PROOF_OF_WORK_DIFFICULTY = 3;
    private String TRANSACTION_POOL_DATA = "111111";

    @Autowired
    BlockchainDataRepository blockchainDataRepository;

    @Autowired
    BlockDataRepository blockDataRepository;

    @Autowired
    TransactionPoolRepository transactionPoolRepository;

    public Blockchain() {
        this.transactionPool = new TransactionPool();
        initializeChains();
    }

    private void initializeChains() {
        this.chains = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction, PublicKey senderPublicKey, String signature) throws Exception {
        if (!verifyTransaction(transaction, senderPublicKey, signature)) {
            throw new Exception("認証情報が不正です。");
        }
        this.transactionPool.addTransaction(transaction);
        saveTransactionPool();
    }

    public boolean verifyTransaction(Transaction transaction, PublicKey senderPublicKey, String signature) throws Exception {

        String transactionHash = Sha256Algorithm.sha256(transaction.toString());

        // 署名生成アルゴリズムを指定する
        Signature dsa = Signature.getInstance("SHA1withECDSA");
        // 初期化
        dsa.initVerify(senderPublicKey);
        // 署名検証する対象をセットする
        dsa.update(transactionHash.getBytes("UTF-8"));
        // 署名検証
        boolean verifyResult = dsa.verify(DatatypeConverter.parseHexBinary(signature));
        return verifyResult;
    }

    public void mining(String minerBlockchainAddress) throws Exception {
        addMiningRewardTransaction(minerBlockchainAddress);
        int nonce = proofOfWork();
        String prevHash = getPrevHash();
        Block newBlock = new Block(this.transactionPool.clone().getTransactions(), nonce, prevHash);
        this.chains.add(newBlock);
        this.transactionPool.clear();
        saveTransactionPool();
        saveBlock(newBlock);
    }

    public double calculateTotalAmount(String blockchainAddress) {
        double totalAmount = 0.0;
        for (Block chain:  chains){
            for (Transaction transaction : chain.getTransactions()) {
                if (transaction.getRecipientBlockchainAddress().equals(blockchainAddress)) {
                    totalAmount += transaction.getValue();
                } else if (transaction.getSenderBlockchainAddress().equals(blockchainAddress)) {
                    totalAmount -= transaction.getValue();
                }
            }
        }
        return totalAmount;
    }

    private String getPrevHash() {
        return this.chains.get(chains.size() - 1).hash();
    }

    public int proofOfWork() throws CloneNotSupportedException {
        String prevHash = getPrevHash();
        int nonce = 0;
        while (!validProof(prevHash, nonce)) {
            nonce += 1;
        }
        return nonce;
    }

    private boolean validProof(String prevHash, int nonce) throws CloneNotSupportedException {
        Block guessBlock = new Block(this.transactionPool.clone().getTransactions(), nonce, prevHash);
        if (guessBlock.hash().substring(0, PROOF_OF_WORK_DIFFICULTY).equals(StringUtils.repeat('0', PROOF_OF_WORK_DIFFICULTY))) {
            return true;
        }
        return false;
    }

    private void addMiningRewardTransaction(String minerBlockchainAddress) {
        Transaction miningRewardTransaction = createMiningRewardTransaction(minerBlockchainAddress);
        this.transactionPool.addTransaction(miningRewardTransaction);
    }

    private Transaction createMiningRewardTransaction(String minerBlockchainAddress) {
        Transaction miningRewardTransaction = new Transaction();
        miningRewardTransaction.setSenderBlockchainAddress(MINING_SENDER_ADDRESS);
        miningRewardTransaction.setRecipientBlockchainAddress(minerBlockchainAddress);
        miningRewardTransaction.setValue(MINING_REWARD);
        return miningRewardTransaction;
    }

    private void saveData() {
//        BlockchainData data = new BlockchainData();
//        data.setChains(convertBlockToEntity(this.chains));
//        data.setTransactions(convertTransactionToEntity(this.transactionPool.getTransactions()));
//        blockchainDataRepository.save(data);
//        saveTransactionPool(transactionPool);
    }

    private void saveTransactionPool() {
        TransactionPoolData transactionPoolData = new TransactionPoolData();
        transactionPoolData.setId(TRANSACTION_POOL_DATA);
        transactionPoolData.setTransactions(convertTransactionToEntity(this.transactionPool.getTransactions()));
        transactionPoolRepository.save(transactionPoolData);
    }

    private void saveBlock(Block block) {
        BlockData blockData = convertBlockToEntity(block);
        blockDataRepository.save(blockData);
    }

    private List<BlockData> convertBlockToEntity(List<Block> chains) {
        List<BlockData> res = new ArrayList<>();
        for (Block chain : chains) {
            BlockData blockData = new BlockData();
            blockData.setTimestamp(chain.getTimestamp());
            blockData.setPrevHash(chain.getPrevHash());
            blockData.setNonce(chain.getNonce());
            blockData.setTransactions(convertTransactionToEntity(chain.getTransactions()));
            res.add(blockData);
        }
        return res;
    }

    private BlockData convertBlockToEntity(Block block) {
        BlockData blockData = new BlockData();
        blockData.setTimestamp(block.getTimestamp());
        blockData.setPrevHash(block.getPrevHash());
        blockData.setNonce(block.getNonce());
        blockData.setTransactions(convertTransactionToEntity(block.getTransactions()));
        return blockData;
    }

    private List<TransactionData> convertTransactionToEntity(List<Transaction> transactions) {
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
}
