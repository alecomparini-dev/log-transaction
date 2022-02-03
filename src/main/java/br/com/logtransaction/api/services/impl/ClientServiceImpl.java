package br.com.logtransaction.api.services.impl;
import br.com.logtransaction.api.repositories.ClientRepository;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clienteRepository;

}
