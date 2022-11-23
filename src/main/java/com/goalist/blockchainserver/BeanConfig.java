package com.goalist.blockchainserver;

import com.goalist.blockchainserver.entity.BlockData;
import com.goalist.blockchainserver.entity.TransactionData;
import com.goalist.blockchainserver.entity.TransactionPoolData;
import com.goalist.blockchainserver.model.Block;
import com.goalist.blockchainserver.model.Transaction;
import com.goalist.blockchainserver.model.TransactionPool;
import com.goalist.blockchainserver.repository.BlockDataRepository;
import com.goalist.blockchainserver.repository.BlockchainDataRepository;
import com.goalist.blockchainserver.repository.TransactionPoolRepository;
import com.goalist.blockchainserver.service.Blockchain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.goalist.blockchainserver.util.Converter.*;

@Configuration
public class BeanConfig {

    @Autowired
    BlockchainDataRepository blockchainDataRepository;

    @Autowired
    BlockDataRepository blockDataRepository;

    @Autowired
    TransactionPoolRepository transactionPoolRepository;

    private String TRANSACTION_POOL_DATA = "111111";

    @Bean
    public Blockchain blockchain() {
        Blockchain blockchain = new Blockchain();
        List<BlockData> blockDataList = blockDataRepository.findAll();
        if (blockDataList.size() > 0) {
            blockchain.setChains(convertBlockFromEntity(blockDataList));
        } else {
            Block block = new Block(new ArrayList<>(), 1, "");
            block.setPrevHash(block.hash());
            blockchain.setChains(new ArrayList<>(Arrays.asList(block)));
            blockDataRepository.save(convertBlockToEntity(block));
        }
        Optional<TransactionPoolData> transactionPoolData = transactionPoolRepository.findById(TRANSACTION_POOL_DATA);
        if (transactionPoolData.isPresent()) {
            blockchain.setTransactionPool(
                    new TransactionPool(convertTransactionFromEntity(
                            transactionPoolData.get().getTransactions())));
        }
        return blockchain;
    }
}
