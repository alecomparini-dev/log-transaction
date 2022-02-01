package br.com.logtransaction.api.controllers;

import br.com.logtransaction.api.controllers.mappers.TopExpensesByBrandMapper;
import br.com.logtransaction.api.controllers.responses.TopExpensesByBrandResponse;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<TopExpensesByBrandResponse> getClientExpenses() {
        List<TopExpensesByBrand> topExpensesByBrands = clientService.getTopExpesesByBrand(LocalDateTime.now().minusMinutes(5), LocalDateTime.now());
        return topExpensesByBrands.stream().map(TopExpensesByBrandMapper.INSTANCE::topExpensesToResponse).collect(Collectors.toList());
    }
}
