package br.com.logtransaction.api.services.impl;
import br.com.logtransaction.api.models.Client;
import br.com.logtransaction.api.repositories.ClientRepository;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    @Override
    public Map<String,Client> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public void save(Client client) {
        clienteRepository.save(client);
    }

}
