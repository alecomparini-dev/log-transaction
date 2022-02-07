package br.com.logtransaction.api.enums;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum Brand {

    MASTERCARD("mastercard"),
    VISA("visa"),
    ELO("elo"),
    AMERICANEXPRESS("americanexpress");

    private String description;
    private static String allowedValues = "" ;

    private Brand(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }

    public static String getAllowedValues() {
        allowedValues = "";

        String teste = Arrays.stream(Brand.values())
                .map( e -> e.getDescription() ).collect(Collectors.joining(","));

        System.out.println(teste);

        Arrays.stream(Brand.values()).forEach( e -> allowedValues += e.name().toString() + ", ");
        return String.format("[%s]",allowedValues.substring(0,allowedValues.length()-2));
    }

}
