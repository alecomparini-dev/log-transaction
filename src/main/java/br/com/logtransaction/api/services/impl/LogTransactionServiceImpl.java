package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.repositories.LogTransactionRepository;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class LogTransactionServiceImpl implements LogTransactionService {

    @Autowired
    private LogTransactionRepository logTransactionRepository;

    @Override
    public LogTransaction save(LogTransaction logTransaction) {
        logTransaction.setCreateAt(LocalDateTime.now());
        validateFields(logTransaction);
        return this.logTransactionRepository.save(logTransaction);
    }

    private Boolean validateFields(LogTransaction logTransaction) {
        List<String> errors = new ArrayList<>();
        validateBrand(logTransaction, errors);
        validateClient(logTransaction, errors);
        validateAmount(logTransaction, errors);
        validateTransactionDate(logTransaction, errors);

        if(!errors.isEmpty())
            throw new BadRequestException(errors);

        return true;
    }

    private void validateTransactionDate(LogTransaction logTransaction, List<String> errors) {
        if(!Optional.ofNullable(logTransaction.getTransactionDate()).isPresent() ) {
            errors.add("Field transactionDate is required");
            return;
        }

        if(logTransaction.getTransactionDate().isAfter(LocalDateTime.now()) )
            errors.add("Field transactionDate cannot be a future date");
    }

    private void validateAmount(LogTransaction logTransaction, List<String> errors) {
        if(!Optional.ofNullable(logTransaction.getAmount()).isPresent() ) {
            errors.add("Field amount is required");
            return;
        }

        if(logTransaction.getAmount().compareTo(BigDecimal.ZERO) < 0)
            errors.add("Field amount must be positive");
    }

    private void validateBrand(LogTransaction logTransaction, List<String> errors) {
        if(!Optional.ofNullable(logTransaction.getBrand()).isPresent() )
            errors.add("Field brand is required");
//        else
//            if(!Arrays.stream(Brand.values()).anyMatch(
//                    e -> e.name().equalsIgnoreCase(logTransaction.getBrand().name())))
//                errors.add("Field brand not valid. Allowed values: " + Brand.getAllowedValues());
    }

    private void validateClient(LogTransaction logTransaction, List<String> errors) {
        if(!Optional.ofNullable(logTransaction.getClient()).isPresent()) {
            errors.add("Field client is required");
            return;
        }

        if(logTransaction.getClient().isEmpty() )
            errors.add("Field client is not empty");

        if(!logTransaction.getClient().toLowerCase().startsWith("client") )
            errors.add("Field client must start with Client");
    }
    
}
