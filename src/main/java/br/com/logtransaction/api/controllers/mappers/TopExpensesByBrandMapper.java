package br.com.logtransaction.api.controllers.mappers;
import br.com.logtransaction.api.controllers.responses.TopExpensesByBrandResponse;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopExpensesByBrandMapper {

    TopExpensesByBrandMapper INSTANCE = Mappers.getMapper( TopExpensesByBrandMapper.class );

    @Mapping(source = "brand", target = "operadora")
    @Mapping(source = "client", target = "cliente")
    @Mapping(source = "amount", target = "valorTotal")
    TopExpensesByBrandResponse topExpensesToResponse(TopExpensesByBrand topExpensesByBrand);
}
