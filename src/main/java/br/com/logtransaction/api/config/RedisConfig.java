package br.com.logtransaction.api.config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

import br.com.logtransaction.api.models.TopExpensesByBrand;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("localhost", 6379);
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPassword("redis");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jedisConnectionFactory.setUseSsl(false);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, TopExpensesByBrand> redisTemplate() {
        RedisTemplate<String, TopExpensesByBrand> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }






}
