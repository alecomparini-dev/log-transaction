package br.com.logtransaction.api.repositories;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.models.TopExpensesByBrand;


@Repository
public class ClientRepository{ 

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ClientRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate){
        

        Query query = new Query();
        query.addCriteria(Criteria.where("brand").is("visa"));
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(mongoTemplate.findOne(query, LogTransaction.class));
            

        return null;
    }


}



// public interface ClientRepository extends MongoRepository<TopExpensesByBrand, String>{
    // @Aggregation(pipeline = {
    //     "{'$match': { 'transactionDate': {$gte: '?0' , $lte: ?1 }}}"
    // })
    // List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate);