package br.com.logtransaction.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.exceptions.BadRequestException;

@RestController
public class LogTransactionController {
    
    @Autowired
    private LogTransactionService logTransactionService;

    @PostMapping("/log")
    public ResponseEntity<LogTransaction> save(@Valid @RequestBody LogTransaction logTransaction, BindingResult result) {
        if (result.hasErrors())
			throw new BadRequestException(result);
            
        return ResponseEntity.ok(this.logTransactionService.save(logTransaction));
    }
    
}
