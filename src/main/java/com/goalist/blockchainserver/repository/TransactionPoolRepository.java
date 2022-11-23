package com.goalist.blockchainserver.repository;

import com.goalist.blockchainserver.entity.TransactionData;
import com.goalist.blockchainserver.entity.TransactionPoolData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionPoolRepository extends MongoRepository<TransactionPoolData, String> {

}
