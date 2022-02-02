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
import org.springframework.stereotype.Service;


@Service
public class TopExpensesByBrandServiceImpl implements TopExpensesByBrandService {

    @Autowired
    private TopExpensesByBrandRepository topExpensesRepository;

    @Autowired
    private TopExpensesCachedRepository topExpensesCachedRepository ;

    @Override
    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startTime, LocalDateTime endTime) {
        List<TopExpensesByBrand> topExpensesByBrands = new ArrayList<>();

        Map<String, TopExpensesByBrand> topExpensesCached = getTopExpensesCached();
        if (topExpensesCached.isEmpty()) {
            topExpensesByBrands = getTopExpenses(topExpensesRepository.getTopExpesesByBrand(startTime, endTime));
            saveTopExpensesCached(topExpensesByBrands);
        }
        topExpensesByBrands = topExpensesCached.values().stream().collect(Collectors.toList());

        return topExpensesByBrands;
    }

    private Map<String, TopExpensesByBrand> getTopExpensesCached() {
        Map<String, TopExpensesByBrand> topExpensesByBrand = topExpensesCachedRepository.getExpensesByBrandCached();
        return topExpensesByBrand;
    }

    private void saveTopExpensesCached(List<TopExpensesByBrand> topExpensesByBrands) {
        topExpensesByBrands.stream()
            .forEach( topExpenses -> {
                topExpensesCachedRepository.save(topExpenses);
            });
    }


    private List<TopExpensesByBrand> getTopExpenses(List<TopExpensesByBrand> listTopExpenses) {
        Map<Brand,List<TopExpensesByBrand>> mapTopExpenses = listTopExpenses.stream()
            .collect(groupingBy(TopExpensesByBrand::getBrand)
        );
        
        List<TopExpensesByBrand> topExpenses = new ArrayList<>();
        mapTopExpenses.entrySet()
            .stream()
            .forEach( entry -> {
                topExpenses.add(entry.getValue().stream()
                .max(Comparator.comparing(TopExpensesByBrand::getAmount))
                .orElse(null));
            });
        
        return topExpenses;
    }

}
