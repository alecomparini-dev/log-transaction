package br.com.logtransaction.api.services;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import java.time.LocalDateTime;
import java.util.List;

public interface ClientService {
    public List<TopExpensesByBrand> getTopExpesesByBrand(LocalDateTime startDate, LocalDateTime limitDate);
}
