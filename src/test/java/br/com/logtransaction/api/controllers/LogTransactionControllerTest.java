package br.com.logtransaction.api.controllers;

import br.com.logtransaction.LogtransactionApplication;
import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.services.LogTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ LogtransactionApplication.class })
public class TestController {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private LogTransactionService logTransactionService;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_CreateAccount_When_ValidRequest() throws Exception {

        when(logTransactionService.save(any(LogTransaction.class)))
                .thenReturn(LogTransaction.builder()
                        .Id("61f96fd8df7507221539a736")
                        .brand(Brand.ELO)
                        .amount(BigDecimal.valueOf(12654.55))
                        .client("Alessandro Comparini")
                        .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                        .createAt(LocalDateTime.parse("2022-01-29T19:14:18.129000"))
                .build());

        mockMvc.perform(post("/log").contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"brand\": \"ELO\",\n" +
                                "    \"client\": \"Alessandro Comparini\",\n" +
                                "    \"amount\": 12654.55,\n" +
                                "    \"transactionDate\": \"2022-01-29T18:14:18.129000\" \n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(header().string("Location", "/api/account/12345"))
                .andExpect(jsonPath("$.client").value("Alessandro Comparini"))
                .andExpect(jsonPath("$.brand").value("ELO"))
                .andExpect(jsonPath("$.amount").value("12654.55"))
                .andExpect(jsonPath("$.transactionDate").value("2022-01-29T18:14:18.129000"));
    }


}
