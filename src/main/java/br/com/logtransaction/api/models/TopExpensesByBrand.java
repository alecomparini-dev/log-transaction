package br.com.logtransaction.api.models;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document( collection = "logTransaction")
public class TopExpensesByBrand implements Serializable {

    private static final long serialVersionUID = -7156526077883281623L;

    private String brand;
    private String client;
    private BigDecimal amount;
}
