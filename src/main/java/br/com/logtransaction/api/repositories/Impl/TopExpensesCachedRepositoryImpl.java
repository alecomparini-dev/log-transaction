package br.com.logtransaction.api.repositories.Impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TopExpensesCachedRepositoryImpl implements TopExpensesCachedRepository {


    private RedisTemplate<String, TopExpensesByBrand> redisTemplate;
    private HashOperations hashOperations;

    public TopExpensesCachedRepositoryImpl(RedisTemplate<String, TopExpensesByBrand> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public Map<String, TopExpensesByBrand> getExpensesByBrandCached() {
        Map<String,TopExpensesByBrand> topExpensesCached = hashOperations.entries("TOPEXPENSES");
        return topExpensesCached;
    }

    @Override
    public void save(TopExpensesByBrand topExpensesByBrand) {
        hashOperations.put("TOPEXPENSES", topExpensesByBrand.getBrand(), topExpensesByBrand);
    }

}