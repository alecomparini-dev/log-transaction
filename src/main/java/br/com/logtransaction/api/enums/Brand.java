package br.com.logtransaction.api.enums;
import java.util.Arrays;

public enum Brand {

    MASTERCARD("mastercard"),
    VISA("visa"),
    ELO("elo"),
    AMERICANEXPRESS("americanexpress");

    private String description;
    private String allowedValues = "" ;

    private Brand(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAllowedValues() {
        allowedValues = "";
        Arrays.stream(Brand.values()).forEach( e -> allowedValues += e.name().toString() + ", ");
        return String.format("[%s]",allowedValues.substring(0,allowedValues.length()-2));
    }

}
