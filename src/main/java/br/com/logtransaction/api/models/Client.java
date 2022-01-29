package br.com.logtransaction.api.models;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Client {

    private String brand;
    private String client;
    private BigDecimal amount; 
    
}
