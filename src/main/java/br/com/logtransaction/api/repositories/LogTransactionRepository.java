package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.models.LogTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogTransactionRepository extends MongoRepository<LogTransaction,String> {}
