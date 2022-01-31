package br.com.logtransaction.api.models;
import lombok.Data;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "logtransaction")
public class TopExpensesByBrand {
    private String brand;
    private String client;
    private BigDecimal amount;
}
