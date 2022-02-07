package br.com.logtransaction.api.controllers.requests;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthRequest {
    private String user;
    private String password;
}
