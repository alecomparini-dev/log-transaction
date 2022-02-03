package br.com.logtransaction.api.repositories.Impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TopExpensesCachedRepositoryImpl implements TopExpensesCachedRepository {
    private static final String KEY_REDIS = "topExpenses";

    @Value("${api.cache.redis.expirate}")
    private int expirate;

    @Autowired
    private RedisTemplate<String, TopExpensesByBrand> redisTemplate;

    @Resource(name="redisTemplate")
    private HashOperations<String, String,  TopExpensesByBrand> hashOperations;

    public List<TopExpensesByBrand> getExpensesByBrandCached() {
        Map<String,TopExpensesByBrand> topExpensesCached = hashOperations.entries(KEY_REDIS);
        return topExpensesCached.values().stream().collect(Collectors.toList());
    }

    @Override
    public void save(TopExpensesByBrand topExpensesByBrand) {
        hashOperations.put(KEY_REDIS, topExpensesByBrand.getBrand().getDescription(), topExpensesByBrand);
        redisTemplate.expire(KEY_REDIS, Duration.ofSeconds(expirate));
    }

}