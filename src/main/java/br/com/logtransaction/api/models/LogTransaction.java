package br.com.logtransaction.api.models;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
@Document
public class LogTransaction implements Serializable{

    private static final long serialVersionUID = -7156526077883281623L;
    
    @Id
    private String Id;
    
    @NotNull(message = "Field brand is required")
    @NotEmpty(message = "Field brand is not empty")
    @Indexed(direction = IndexDirection.ASCENDING)
    private String brand;
    
    @NotNull(message = "Field client is required")
    @NotEmpty(message = "Field client is not empty")
    private String client;
    
    @NotNull(message = "Field amount is required")
    @PositiveOrZero(message = "")
    private BigDecimal amount;

    @NotNull(message = "Field transactionDate is required")
    @PastOrPresent(message = "Field transactionDate not valid")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime transactionDate;
    private LocalDateTime createAt;
    
}
