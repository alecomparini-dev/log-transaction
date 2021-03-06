package br.com.logtransaction.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtGenerator {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    @Value("${api.security.jwt.expirate-token}")
    private int expirateToken;

    public JwtGenerator(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String createJwt(String subject) {
        JWTCreator.Builder jwtBuilder = JWT.create().withSubject(subject);

        return jwtBuilder
                .withNotBefore(new Date())
                .withExpiresAt(this.expirationDate(expirateToken))
                .sign(Algorithm.RSA256(publicKey, privateKey));
    }

    private Date expirationDate(int afterSeconds) {
        return expirationDate(Instant.now(), afterSeconds);
    }

    private Date expirationDate(Instant now, int afterSeconds) {
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.setTimeInMillis(Instant.now().toEpochMilli());
        expirationTime.add(Calendar.SECOND, afterSeconds);

        return expirationTime.getTime();
    }
}
