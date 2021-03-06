package br.com.logtransaction.api.models;

import br.com.logtransaction.api.enums.Brand;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@Document(collection = "logTransaction")
public class LogTransaction implements Serializable{

    private static final long serialVersionUID = -7156526077883281623L;

    @MongoId
    private String Id;

    @NotNull(message = "Field brand is required")
    private Brand brand;

    @NotNull(message = "Field client is required")
    @NotEmpty(message = "Field client is not empty")
    private String client;

    @NotNull(message = "Field amount is required")
    @PositiveOrZero(message = "Field amount must be positive")
    private BigDecimal amount;

    @PastOrPresent(message = "Field transactionDate not valid")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @NotNull(message = "Field transactionDate is required")
    private LocalDateTime transactionDate;
    private LocalDateTime createAt;
    
}
