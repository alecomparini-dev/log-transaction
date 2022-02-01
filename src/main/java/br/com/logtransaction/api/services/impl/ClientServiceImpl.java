package br.com.logtransaction.api.services.impl;
import static java.util.stream.Collectors.groupingBy;
import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.repositories.ClientRepository;
import br.com.logtransaction.api.services.ClientService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    @Override
    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate) {
        return getTopExpenses(clienteRepository.getTopExpesesByBrand(startDate, limitDate));
    }


    private List<TopExpensesByBrand> getTopExpenses(List<TopExpensesByBrand> listTopExpenses) {
        Map<Brand,List<TopExpensesByBrand>> tops = listTopExpenses.stream()
            .collect(groupingBy(TopExpensesByBrand::getBrand)
        );
        
        List<TopExpensesByBrand> maxExpenses = new ArrayList<>();

        tops.entrySet()
            .stream()
            .forEach( entry -> {
                maxExpenses.add(entry.getValue().stream()
                .max(Comparator.comparing(TopExpensesByBrand::getAmount))
                .orElse(null));
            });

        return maxExpenses;
    }



}
