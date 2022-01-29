package br.com.logtransaction.api.services.impl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.repositories.LogTransactionRepository;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.exceptions.BadRequestException;


@Service
public class LogTransactionServiceImpl implements LogTransactionService {

    @Autowired
    private LogTransactionRepository logTransactionRepository;
    
    @Override
    public LogTransaction save(LogTransaction logTransaction) {
        return this.logTransactionRepository.save(validateRequest(logTransaction));
    }


    private LogTransaction validateRequest(LogTransaction logTransaction) {
        List<String> errors = new ArrayList<>();
        validateBrand(logTransaction, errors);
        validateClient(logTransaction, errors);
        if(!errors.isEmpty())
            throw new BadRequestException(errors);

        return logTransaction;
    }

    private void validateBrand(LogTransaction logTransaction, List<String> errors) {
        if( !logTransaction.getClient().toLowerCase().startsWith("client") )
            errors.add("Field 'client' must start with Client");
    }

    private void validateClient(LogTransaction logTransaction, List<String> errors) {
        if(!Arrays.stream(Brand.values()).anyMatch(
            e -> e.name().equalsIgnoreCase(logTransaction.getBrand())))
           errors.add("Field 'brand' not valid. Allowed values: " + Brand.getAllowedValues());
    }
    
}
