package br.com.logtransaction.api.repositories.Impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TopExpensesCachedRepositoryImpl implements TopExpensesCachedRepository {


    @Autowired
    private RedisTemplate<String, TopExpensesByBrand> template;
    @Resource(name="redisTemplate")
    private HashOperations<String, String,  TopExpensesByBrand> hashOperations;

    public List<TopExpensesByBrand> getExpensesByBrandCached() {
        Map<String,TopExpensesByBrand> topExpensesCached = hashOperations.entries("TOPEXPENSES");
        return topExpensesCached.values().stream().collect(Collectors.toList());
    }

    @Override
    public void save(TopExpensesByBrand topExpensesByBrand) {
        hashOperations.put("TOPEXPENSES", topExpensesByBrand.getBrand().getDescription(), topExpensesByBrand);
    }

}