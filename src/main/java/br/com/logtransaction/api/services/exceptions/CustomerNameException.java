package br.com.logtransaction.api.services.exceptions;

public class CustomerNameException extends RuntimeException{

    public CustomerNameException(String msg) {
        super(msg);
    }
}
