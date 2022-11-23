package com.goalist.blockchainserver.entity;

import com.goalist.blockchainserver.model.Block;
import com.goalist.blockchainserver.model.TransactionPool;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class BlockchainData {

    @Id
    private String id;

    private List<TransactionData> transactions;

    private List<BlockData> chains;
}
