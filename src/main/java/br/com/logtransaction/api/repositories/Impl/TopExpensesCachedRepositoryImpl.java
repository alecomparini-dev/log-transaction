package br.com.logtransaction.api.repositories.Impl;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;

import java.util.Map;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TopExpensesCachedRepositoryImpl implements TopExpensesCachedRepository{

    private RedisTemplate<String, TopExpensesByBrand> redisTemplate;
    private HashOperations hashOperations;

    public TopExpensesCachedRepositoryImpl(RedisTemplate<String, TopExpensesByBrand> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public Map<String, TopExpensesByBrand> getExpensesByBrand() {
        Map<String,TopExpensesByBrand> list = hashOperations.entries("topExpenses");
        return list;
    }

    public void save(TopExpensesByBrand topExpenses) {
        hashOperations.put("topExpenses", topExpenses.getBrand(), topExpenses);
    }

}