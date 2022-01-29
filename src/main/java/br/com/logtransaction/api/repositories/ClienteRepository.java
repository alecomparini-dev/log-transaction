package br.com.logtransaction.api.repositories;
import br.com.logtransaction.api.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteRepository {

    @Autowired
    private RedisTemplate<String, Client> redisTemplate;

    public void save(Client client) {
//        redisTemplate.opsForValue().set(client.getId(), client);
        Long teste = redisTemplate.opsForSet().add(client.getId(), client);
        System.out.println(teste.toString());

    }

}
