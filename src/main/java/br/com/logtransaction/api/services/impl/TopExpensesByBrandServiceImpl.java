package br.com.logtransaction.api.services.impl;
import static java.util.stream.Collectors.groupingBy;
import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.TopExpensesByBrandRepository;
import br.com.logtransaction.api.repositories.TopExpensesCachedRepository;
import br.com.logtransaction.api.services.TopExpensesByBrandService;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;


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
            topExpensesByBrands = groupTopExpensesByBrand(topExpensesRepository.getExpesesByTime(startTime, endTime));
//            topExpensesByBrands = topExpensesRepository.getExpesesByTime(startTime, endTime);
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

    private List<TopExpensesByBrand> groupTopExpensesByBrand(List<TopExpensesByBrand> listTopExpenses) {
        List<TopExpensesByBrand> topExpenses = new ArrayList<>();
        listTopExpenses.stream().collect(groupingBy(TopExpensesByBrand::getBrand))
                .entrySet()
                .stream()
                .forEach(entry -> {
                    topExpenses.add(
                        entry.getValue()
                            .stream()
                            .max(Comparator.comparing(TopExpensesByBrand::getAmount))
                            .orElse(null));
                });

        return topExpenses;
    }

}
