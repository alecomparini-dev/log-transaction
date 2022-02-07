package br.com.logtransaction.api.controllers;

import br.com.logtransaction.api.controllers.mappers.TopExpensesByBrandMapper;
import br.com.logtransaction.api.controllers.responses.TopExpensesByBrandResponse;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.services.TopExpensesByBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TopExpensesByBrandController {

    @Value("${api.parameter.time.get-top-expenses}")
    private Integer time;

    @Autowired
    private TopExpensesByBrandService topExpensesByBrandService;

    @GetMapping("/client")
    public List<TopExpensesByBrandResponse> getClientExpenses() {
        List<TopExpensesByBrand> topExpensesByBrands = topExpensesByBrandService.getTopExpensesByBrand(LocalDateTime.now().minusMinutes(time), LocalDateTime.now());
        return topExpensesByBrands.stream().map(TopExpensesByBrandMapper.INSTANCE::topExpensesToResponse).collect(Collectors.toList());
    }
}
