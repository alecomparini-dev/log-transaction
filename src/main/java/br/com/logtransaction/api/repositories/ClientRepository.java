package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends MongoRepository<TopExpensesByBrand, String> {
     @Aggregation(pipeline = {
        "{$addFields: {startTime: {$convert: {input: '?0',to: 'date'}}, endTime: {$convert: {input: '?1',to: 'date'}},doubleAmount: {$convert: {input: '$amount',to: 'decimal'}}}}",
        "{$project: {brand: '$brand',client: '$client',amount: '$doubleAmount',filter: {$and: [{$gte: ['$transactionDate','$startTime']},{$lte: ['$transactionDate','$endTime']}]}}}",
        "{$match: {filter: {$eq: true}}}"
     })
     List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime);

     

}
//
//@Repository
//public class ClientRepository {
//
//    @Autowired
//    MongoTemplate mongoTemplate;
//
//    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate) {
//
//        GroupOperation group = group();
//        ProjectionOperation projectionOperation = project("");
//        Aggregation agg = newAggregation(
//
//                )
//
//        );
//
//        //Convert the aggregation result into a List
//        AggregationResults<TopExpensesByBrand> groupResults
//                = mongoTemplate.aggregate(agg, LogTransaction.class, TopExpensesByBrand.class);
//        List<TopExpensesByBrand> result = groupResults.getMappedResults();
//
//        return result;
//
//    }
//}




