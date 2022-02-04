package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.repositories.LogTransactionRepository;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.TopExpensesByBrandService;
import br.com.logtransaction.api.services.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Service
public class LogTransactionServiceImpl implements LogTransactionService {

    @Autowired
    private LogTransactionRepository logTransactionRepository;

    @Override
    public LogTransaction save(LogTransaction logTransaction) {
        logTransaction.setCreateAt(LocalDateTime.now());
        logTransaction = this.logTransactionRepository.save(validateRequest(logTransaction));
        return logTransaction;
    }

    private LogTransaction validateRequest(LogTransaction logTransaction) {
        List<String> errors = new ArrayList<>();
        validateClient(logTransaction, errors);
        if(!errors.isEmpty())
            throw new BadRequestException(errors);
        return logTransaction;
    }

    private void validateClient(LogTransaction logTransaction, List<String> errors) {
        if( !logTransaction.getClient().toLowerCase().startsWith("client") )
            errors.add("Field client must start with Client");
    }
    
}
