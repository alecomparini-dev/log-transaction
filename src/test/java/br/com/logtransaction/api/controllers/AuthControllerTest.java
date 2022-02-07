package br.com.logtransaction.api.controllers;

import br.com.logtransaction.LogtransactionApplication;
import br.com.logtransaction.api.controllers.requests.AuthRequest;
import br.com.logtransaction.api.security.JwtGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ LogtransactionApplication.class })
public class AuthControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @MockBean
    private JwtGenerator jwtGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_return_200_when_valid_user_and_password() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .user("user")
                .password("password")
                .build();

        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9";
        when(this.jwtGenerator.createJwt(any(String.class)))
                .thenReturn(jwt);

        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.access_token").value(jwt));
    }

    @Test
    public void should_return_401_when_invalid_user() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .user("user_invalid")
                .password("password")
                .build();

        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());

        verify(this.jwtGenerator, Mockito.times(0)).createJwt(any(String.class));
    }

    @Test
    public void should_return_401_when_invalid_password() throws Exception {
        AuthRequest authRequest = AuthRequest.builder()
                .user("user")
                .password("password_invalid")
                .build();

        this.mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized());

        verify(this.jwtGenerator, Mockito.times(0)).createJwt(any(String.class));
    }

}
