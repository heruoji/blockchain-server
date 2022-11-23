package com.goalist.blockchainserver.repository;

import com.goalist.blockchainserver.entity.BlockData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BlockDataRepository extends MongoRepository<BlockData, String> {
    List<BlockData> findAllOrderByTimestamp();
}
