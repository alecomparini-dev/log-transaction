package br.com.logtransaction.api.controllers;

import br.com.logtransaction.LogtransactionApplication;
import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.LogTransaction;
import br.com.logtransaction.api.resources.DefaulErrorExceptionHandler;
import br.com.logtransaction.api.resources.DefaultError;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.exceptions.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ LogtransactionApplication.class })
public class LogTransactionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private LogTransactionService logTransactionService;

    @MockBean
    private DefaulErrorExceptionHandler defaulErrorExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void should_return_400_when_throw_badRequest_in_service() throws Exception {
        LogTransaction log = LogTransaction.builder()
                .Id("61f96fd8df7507221539a736")
                .brand(Brand.ELO)
                .amount(BigDecimal.valueOf(12654.55))
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .createAt(LocalDateTime.parse("2022-01-29T19:14:18.129000"))
                .build();

        List<String> errors = new ArrayList<>();
        errors.add("Field client is required");
        BadRequestException badRequest = new BadRequestException(errors);
        when(this.logTransactionService.save(any(LogTransaction.class)))
                .thenThrow(badRequest);

        DefaultError defaultError = DefaultError.builder()
                .timestamp(LocalDateTime.parse("2022-02-06T17:08:23.344000"))
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .path("/log")
                .message(badRequest.getMsg())
                .build();

        when(this.defaulErrorExceptionHandler.badRequest(any(BadRequestException.class),any(HttpServletRequest.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultError));

        this.mockMvc.perform(post("/log").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(log))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").value(defaultError.getTimestamp().toString()))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.path").value("/log"))
                .andExpect(jsonPath("$.message").value(defaultError.getMessage()));
    }


    @Test
    public void should_return_200_when_valid_request() throws Exception {
        LogTransaction log = LogTransaction.builder()
                .Id("61f96fd8df7507221539a736")
                .brand(Brand.ELO)
                .amount(BigDecimal.valueOf(12654.55))
                .client("client10")
                .transactionDate(LocalDateTime.parse("2022-01-29T18:14:18.129000"))
                .createAt(LocalDateTime.parse("2022-01-29T19:14:18.129000"))
                .build();

        when(logTransactionService.save(any(LogTransaction.class)))
                .thenReturn(log);

        this.mockMvc.perform(post("/log").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(log))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(header().string("Location", "/api/account/12345"))
                .andExpect(jsonPath("$.client").value("client10"))
                .andExpect(jsonPath("$.brand").value("ELO"))
                .andExpect(jsonPath("$.amount").value("12654.55"))
                .andExpect(jsonPath("$.transactionDate").value("2022-01-29T18:14:18.129000"));
    }


}
