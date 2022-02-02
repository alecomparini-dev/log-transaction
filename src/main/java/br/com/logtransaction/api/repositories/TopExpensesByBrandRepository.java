package br.com.logtransaction.api.repositories;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TopExpensesByBrandRepository extends MongoRepository<TopExpensesByBrand, String> {
   @Aggregation(pipeline = {
      "{ $match: { transactionDate: { $gte:?0, $lte:?1 }}}",
      "{ $project: { brand:'$brand', client:'$client', amount: { $convert: { input:'$amount', to:'decimal' }}}}",
      "{ $group: { _id: { brand:'$brand', client:'$client'}, amount: { $sum:'$amount' }}}",
      "{ $project: { brand:'$_id.brand', client:'$_id.client', amount:'$amount' }}",
   })
   List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime);
}