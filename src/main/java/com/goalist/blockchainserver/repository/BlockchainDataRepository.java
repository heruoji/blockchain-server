package com.goalist.blockchainserver.repository;

import com.goalist.blockchainserver.entity.BlockchainData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlockchainDataRepository extends MongoRepository<BlockchainData, String> {
}
