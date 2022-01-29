package br.com.logtransaction.api.services.impl;
import br.com.logtransaction.api.models.Client;
import br.com.logtransaction.api.repositories.ClienteRepository;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void save(Client client) {
        clienteRepository.save(client);
    }

    @Override
    public Client findById(String id) {
        return null;
    }

}
