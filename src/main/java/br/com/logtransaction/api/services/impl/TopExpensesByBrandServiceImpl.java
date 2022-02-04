package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesByBrandRepository;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import br.com.logtransaction.api.services.TopExpensesByBrandService;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class TopExpensesByBrandServiceImpl implements TopExpensesByBrandService {

    @Autowired
    private TopExpensesByBrandRepository topExpensesRepository;

    @Autowired
    private TopExpensesCachedRepository topExpensesCachedRepository ;

    @Override
    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime) {
        List<TopExpensesByBrand> topExpensesByBrands = topExpensesCachedRepository.getExpensesByBrandCached();
        if (topExpensesByBrands.isEmpty()) {
            topExpensesByBrands = topExpensesRepository.getExpesesByTime(startTime, endTime);
            CompletableFuture<Boolean> cf = CompletableFuture.completedFuture(topExpensesByBrands)
                    .thenApplyAsync( topExpenses -> saveTopExpensesCached(topExpenses));
        }
       return topExpensesByBrands;
    }

    //TODO: Melhorar para salvar tudo de uma única vez
    private Boolean saveTopExpensesCached(List<TopExpensesByBrand> topExpensesByBrands) {
        topExpensesByBrands.stream()
            .forEach( topExpenses -> {
                topExpensesCachedRepository.save(topExpenses);
            });

        return true;
    }

}
