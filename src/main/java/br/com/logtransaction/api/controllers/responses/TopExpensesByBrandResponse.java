package br.com.logtransaction.api.controllers.responses;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TopExpensesByBrandResponse {
    private String operadora;
    private String cliente;
    private BigDecimal valorTotal;
}
