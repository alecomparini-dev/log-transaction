package br.com.logtransaction.api.models;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.mongodb.core.mapping.Document;
import br.com.logtransaction.api.enums.Brand;
import org.springframework.data.redis.core.RedisHash;

@Data
@Document( collection = "logTransaction")
public class TopExpensesByBrand implements Serializable{
    private static final long serialVersionUID = -7156526077883281623L;

    private Brand brand;
    private String client;
    private BigDecimal amount;


    @Override
    public String toString() {
        return "TopExpensesByBrandCached{" + "brand='" + brand + '\'' + ", client='" + client + '\'' + ", amount=" + amount.toString() + '}';
    }
}
