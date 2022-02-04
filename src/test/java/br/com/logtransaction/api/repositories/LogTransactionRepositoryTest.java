package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LogTransactionRepositoryTest {

    @Autowired
    private LogTransactionRepository logTransactionRepository;

    @Test
    public void salvarLogTest() {
        LogTransaction logTransaction = new LogTransaction();
        logTransaction.setBrand(Brand.ELO);
        logTransaction.setAmount(BigDecimal.valueOf(1255.25));
        logTransaction.setClient("Teste OK");
        logTransaction.setTransactionDate(LocalDateTime.parse("2022-02-03T19:17:32.5764941"));

        LogTransaction logResult = logTransactionRepository.save(logTransaction);
        Assert.assertNotNull(logResult);
        Assert.assertTrue(true);

    }
}
