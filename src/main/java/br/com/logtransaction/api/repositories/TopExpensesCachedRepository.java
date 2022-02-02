package br.com.logtransaction.api.repositories;
import java.util.List;
import java.util.Map;
import br.com.logtransaction.api.models.TopExpensesByBrand;

public interface TopExpensesCachedRepository{
    
    public Map<String, TopExpensesByBrand> getExpensesByBrandCached();
    public void save(TopExpensesByBrand topExpensesByBrand);
}
