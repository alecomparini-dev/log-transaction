package br.com.logtransaction.api.services.impl;

import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesByBrandRepository;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import br.com.logtransaction.api.services.TopExpensesByBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


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
            //TODO: incluir completableFuture
            saveTopExpensesCached(topExpensesByBrands);
        }
       return topExpensesByBrands;
    }

    //TODO: Melhorar para salvar tudo de uma única vez
    private void saveTopExpensesCached(List<TopExpensesByBrand> topExpensesByBrands) {
        topExpensesByBrands.stream()
            .forEach( topExpenses -> {
                topExpensesCachedRepository.save(topExpenses);
            });
    }

}
