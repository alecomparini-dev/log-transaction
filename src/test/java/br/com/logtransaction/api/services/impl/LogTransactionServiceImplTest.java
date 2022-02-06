package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.repositories.LogTransactionRepository;
import br.com.logtransaction.api.services.exceptions.BadRequestException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogTransactionServiceImplTest {

    @Mock
    private LogTransactionRepository logTransactionRepository;

    @InjectMocks
    private LogTransactionServiceImpl logTransactionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_throw_exception_when_clientName_is_null() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.ELO)
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field client is required");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_clientname_is_empty() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.ELO)
                .client("")
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        List<String> error = new ArrayList<>();
        try {
            this.logTransactionService.save(logTransaction);
        } catch (BadRequestException e) {
            error = e.getMsg();
        }

        Assert.assertEquals(error.get(0),"Field client is not empty");
        Assert.assertEquals(error.get(1),"Field client must start with Client");
        verify(this.logTransactionRepository, never()).save(Mockito.any());

    }

    @Test
    public void should_throw_exception_when_clientname_does_not_start_with_client() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.ELO)
                .client("Alessandro Comparini")
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field client must start with Client");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_brand_is_null() {
        LogTransaction logTransaction = LogTransaction.builder()
                .client("client test")
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field brand is required");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_amount_is_null() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.AMERICANEXPRESS)
                .client("client test")
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field amount is required");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_transactionDate_is_null() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.AMERICANEXPRESS)
                .client("client test")
                .amount(BigDecimal.valueOf(12654.55))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field transactionDate is required");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_transactionDate_is_future_date() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.AMERICANEXPRESS)
                .client("client test")
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.now().plusMinutes(10))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field transactionDate cannot be a future date");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }

    @Test
    public void should_throw_exception_when_amount_is_negative() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.AMERICANEXPRESS)
                .client("client test")
                .amount(BigDecimal.valueOf(-12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        String error = null;
        try {
            this.logTransactionService.save(logTransaction);
        }catch (BadRequestException e) {
            error = e.getMsg().get(0);
        }

        Assert.assertEquals(error,"Field amount must be positive");
        verify(this.logTransactionRepository, never()).save(Mockito.any());
    }


    @Test
    public void should_save_log_transaction() {
        LogTransaction logTransaction = LogTransaction.builder()
                .brand(Brand.ELO)
                .client("client Alessandro Comparini")
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .build();

        LogTransaction result = LogTransaction.builder()
                .Id("61f96fd8df7507221539a736")
                .brand(Brand.ELO)
                .amount(BigDecimal.valueOf(12654.55))
                .client("client Alessandro Comparini")
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .createAt(LocalDateTime.parse("2022-01-29T19:14:18.129000"))
                .build();

        when(logTransactionRepository.save(any(LogTransaction.class)))
                .thenReturn(result);

        Assert.assertEquals(result,this.logTransactionService.save(logTransaction));
        verify(this.logTransactionRepository, Mockito.times(1)).save(logTransaction);

    }

}