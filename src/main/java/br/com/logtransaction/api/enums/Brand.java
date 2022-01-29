package br.com.logtransaction.api.enums;
import java.util.Arrays;

public enum Brand {

    MASTERCARD,
    VISA,
    ELO,
    AMERICANEXPRESS;

    public static String allowedValues = "" ;
    public static String getAllowedValues() {
        allowedValues = "";
        Arrays.stream(Brand.values()).forEach( e -> allowedValues += e.name().toString() + ", ");
        return String.format("[%s]",allowedValues.substring(0,allowedValues.length()-2));
    }

}
