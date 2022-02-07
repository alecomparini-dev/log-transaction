package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface TopExpensesByBrandRepository extends MongoRepository<TopExpensesByBrand, String> {
   @Aggregation(pipeline = {
        "{ $match: { transactionDate: { $gte:?0, $lte:?1 }}}",
        "{ $project: { brand:'$brand', client:'$client', amount: { $convert: { input:'$amount', to:'decimal' }}}}",
        "{ $group: { _id: { brand:'$brand', client:'$client'}, amount: { $sum:'$amount' }}}",
        "{ $project: { brand:'$_id.brand', client:'$_id.client', amount:'$amount' }}",
        "{ $group: { _id: {brand: '$brand'},amount: {$max: '$amount'},items: {$push: {amount: '$amount',client: '$client'}} }}",
        "{ $project: { brand: '$_id.brand',client: '$_id.items.client',amount: '$amount',items: {$max: '$items'}} }",
        "{ $project: {brand: '$brand',amount: '$amount',client: '$items.client'}}",
   })
   List<TopExpensesByBrand> getTopExpensesByBrand(LocalDateTime startTime, LocalDateTime endTime);
}