package br.com.logtransaction.api.controllers;
import br.com.logtransaction.api.controllers.requests.AuthRequest;
import br.com.logtransaction.api.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String,String> authorize(@RequestBody AuthRequest authRequest ) {

        UserDetails user = this.userDetailsService.loadUserByUsername(authRequest.getUser());

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String jwt = jwtGenerator.createJwt(user.getUsername());

        Map<String,String> result = new HashMap<String,String>();
        result.put("access_token",jwt);
        return result;
    }
}
