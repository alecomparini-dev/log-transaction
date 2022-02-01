package br.com.logtransaction.api.repositories;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopExpensesByBrandRepository extends MongoRepository<TopExpensesByBrand, String> {
     @Aggregation(pipeline = {
        "{$addFields: {startTime: {$convert: {input:'?0', to:'date'}}, endTime: {$convert: {input:'?1', to:'date'}}, doubleAmount: {$convert: {input:'$amount', to:'decimal'}}}}",
        "{$project: {brand: '$brand', client:'$client', amount:'$doubleAmount', filter:{$and: [{$gte: ['$transactionDate','$startTime']}, {$lte: ['$transactionDate','$endTime']}]}}}",
        "{$match: {filter: {$eq:true}}}",
        "{$group: {_id: {brand:'$brand', client:'$client'}, amount: {$sum:'$amount'}}}",
        "{$project: {brand:'$_id.brand', client:'$_id.client', amount:'$amount'}}",
     })
     List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime);
}