package br.com.logtransaction.api.repositories;
import br.com.logtransaction.api.models.Client;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ClientRepository {

    private RedisTemplate<String, Client> redisTemplate;
    private HashOperations hashOperations;

    public ClientRepository(RedisTemplate<String, Client> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public Map<String, Client> findAll() {
        Map<String,Client> list = hashOperations.entries("CLIENT");
        System.out.println(list.size());
        return list;
    }

    public void save(Client client) {
        hashOperations.put("CLIENT", client.getId(), client);
    }

}