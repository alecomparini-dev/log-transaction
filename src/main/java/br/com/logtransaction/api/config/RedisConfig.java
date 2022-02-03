package br.com.logtransaction.api.config;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("localhost", 6379); //TODO: Add properties
        redisStandaloneConfiguration.setDatabase(0); //TODO: Add properties
        redisStandaloneConfiguration.setPassword("redis"); //TODO: Add properties
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, TopExpensesByBrand> redisTemplate() {
        RedisTemplate<String, TopExpensesByBrand> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }



    private RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public CacheManager cacheManager(JedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("topExpenses",createCacheConfiguration(10));

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(createCacheConfiguration(10))
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }



}
