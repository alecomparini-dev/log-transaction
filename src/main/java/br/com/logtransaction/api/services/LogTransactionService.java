package br.com.logtransaction.api.services;
import br.com.logtransaction.api.models.LogTransaction;


public interface LogTransactionService {

    public LogTransaction save(LogTransaction logTransaction);
    
}
