package br.com.logtransaction.api.services;
import br.com.logtransaction.api.models.Client;

public interface ClientService {
    public void save(Client client);
    public Client findById(String id);

}
