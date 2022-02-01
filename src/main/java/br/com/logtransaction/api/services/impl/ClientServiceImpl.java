package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.ClientRepository;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    @Override
    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate) {
        return clienteRepository.getTopExpesesByBrand(startDate, limitDate);
    }
}
