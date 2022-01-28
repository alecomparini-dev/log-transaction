package br.com.logtransaction.model;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Employee {

    @Id
    private String code;
    private String name;
    private Integer age;
    private BigDecimal salary;

    @DBRef //Referencia de uma coletions com outra
    private Employee boss;
        
}
