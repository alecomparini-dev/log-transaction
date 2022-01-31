package br.com.logtransaction.api.models;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Id;
    private String brand;
    private String client;
    private BigDecimal amount;
    private String transactionDate;
}
