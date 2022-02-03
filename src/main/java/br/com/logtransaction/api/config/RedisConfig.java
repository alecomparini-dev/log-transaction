package br.com.logtransaction.api.config;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("localhost", 6379); //TODO: Add properties
        redisStandaloneConfiguration.setDatabase(0); //TODO: Add properties
        redisStandaloneConfiguration.setPassword("redis"); //TODO: Add properties
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        // jedisConnectionFactory.setUseSsl(false);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, TopExpensesByBrand> redisTemplate() {
        RedisTemplate<String, TopExpensesByBrand> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
//        template.setValueSerializer(new GenericToStringSerializer<TopExpensesByBrand>(TopExpensesByBrand.class));
        return template;
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
                .withCacheConfiguration("TOPEXPENSES",
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10)));
    }

}
