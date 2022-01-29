package br.com.logtransaction.api.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import br.com.logtransaction.api.models.LogTransaction;


public interface LogTransactionRepository extends MongoRepository<LogTransaction,String> {
    
}
