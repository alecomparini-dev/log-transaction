package br.com.logtransaction.api.models;
import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Id;
    private String brand;
    private String client;
    private Double amount;
    private String transactionDate;
}
