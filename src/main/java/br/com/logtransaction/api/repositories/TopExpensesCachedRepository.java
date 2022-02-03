package br.com.logtransaction.api.repositories;

import br.com.logtransaction.api.models.TopExpensesByBrand;

import java.util.List;

public interface TopExpensesCachedRepository{
    public List<TopExpensesByBrand> getExpensesByBrandCached();
    public void save(TopExpensesByBrand topExpensesByBrand);
}
