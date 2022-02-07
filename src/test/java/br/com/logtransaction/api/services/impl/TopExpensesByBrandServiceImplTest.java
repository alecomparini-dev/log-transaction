package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.LogTransactionRepository;
import br.com.logtransaction.api.repositories.TopExpensesByBrandRepository;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
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
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopExpensesByBrandServiceImplTest {

    @Mock
    private TopExpensesByBrandRepository topExpensesRepository;

    @Mock
    private TopExpensesCachedRepository topExpensesCachedRepository;

    @InjectMocks
    private TopExpensesByBrandServiceImpl topExpensesByBrandService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_get_top_expenses_in_mongodb_no_cached() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(30);
        LocalDateTime endTime = LocalDateTime.now();
        List<TopExpensesByBrand> noCached = new ArrayList<>();
        when(this.topExpensesCachedRepository.getExpensesByBrandCached())
                .thenReturn(noCached);

        List<TopExpensesByBrand> result = new ArrayList<>();
        result.add(TopExpensesByBrand.builder()
                        .brand(Brand.ELO)
                        .amount(BigDecimal.valueOf(5456.25))
                        .client("client10")
                        .build());
        result.add(TopExpensesByBrand.builder()
                .brand(Brand.MASTERCARD)
                .amount(BigDecimal.valueOf(4588956.25))
                .client("client11")
                .build());

        when(this.topExpensesRepository.getTopExpensesByBrand(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(result);

        Assert.assertEquals(result,this.topExpensesByBrandService.getTopExpensesByBrand(startTime,endTime));
        verify(this.topExpensesCachedRepository, Mockito.times(1)).getExpensesByBrandCached();
        verify(this.topExpensesRepository, Mockito.times(1)).getTopExpensesByBrand(startTime,endTime);
        verify(this.topExpensesCachedRepository, Mockito.timeout(1000).times(2)).save(any());
    }


    @Test
    public void should_get_top_expenses_in_cached() {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(30);
        LocalDateTime endTime = LocalDateTime.now();
        List<TopExpensesByBrand> result = new ArrayList<>();
        result.add(TopExpensesByBrand.builder()
                .brand(Brand.ELO)
                .amount(BigDecimal.valueOf(5456.25))
                .client("client10")
                .build());
        result.add(TopExpensesByBrand.builder()
                .brand(Brand.MASTERCARD)
                .amount(BigDecimal.valueOf(4588956.25))
                .client("client11")
                .build());

        when(this.topExpensesCachedRepository.getExpensesByBrandCached())
                .thenReturn(result);

        Assert.assertEquals(result,this.topExpensesByBrandService.getTopExpensesByBrand(startTime,endTime));
        verify(this.topExpensesCachedRepository, Mockito.times(1)).getExpensesByBrandCached();
        verify(this.topExpensesRepository, Mockito.times(0)).getTopExpensesByBrand(startTime,endTime);
        verify(this.topExpensesCachedRepository, Mockito.times(0)).save(any());

    }


}