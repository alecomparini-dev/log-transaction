package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientRepository extends MongoRepository<TopExpensesByBrand, String> {
     @Aggregation(pipeline = {
        "{$addFields: {startTime: {$convert: {input:'?0', to:'date'}}, endTime: {$convert: {input:'?1', to:'date'}}, doubleAmount: {$convert: {input:'$amount', to:'decimal'}}}}",
        "{$project: {brand: '$brand', client:'$client', amount:'$doubleAmount', filter:{$and: [{$gte: ['$transactionDate','$startTime']}, {$lte: ['$transactionDate','$endTime']}]}}}",
        "{$match: {filter: {$eq:true}}}",
        "{$group: {_id: {brand:'$brand', client:'$client'}, amount: {$sum:'$amount'}}}",
        "{$project: {brand:'$_id.brand', client:'$_id.client', amount:'$amount'}}",
     })
     List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime);
}