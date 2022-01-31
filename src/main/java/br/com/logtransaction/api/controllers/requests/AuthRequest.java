package br.com.logtransaction.api.controllers.requests;
import lombok.Data;

@Data
public class AuthRequest {
    private String user;
    private String password;
}
