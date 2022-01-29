package br.com.logtransaction.api.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

@Data
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    private String Id;
    private String brand;
    private String client;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    
}
