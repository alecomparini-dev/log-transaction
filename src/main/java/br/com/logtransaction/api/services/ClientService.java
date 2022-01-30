package br.com.logtransaction.api.services;
import br.com.logtransaction.api.models.Client;

import java.util.Map;

public interface ClientService {
    public Map<String,Client> findAll();
    public void save(Client client);
}
