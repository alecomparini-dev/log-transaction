package br.com.logtransaction.api.controllers;

import br.com.logtransaction.LogtransactionApplication;
import br.com.logtransaction.api.enums.Brand;
import br.com.logtransaction.api.models.TopExpensesByBrand;
import br.com.logtransaction.api.resources.DefaulErrorExceptionHandler;
import br.com.logtransaction.api.services.LogTransactionService;
import br.com.logtransaction.api.services.TopExpensesByBrandService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ LogtransactionApplication.class })
public class TopExpensesByBrandControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private TopExpensesByBrandService topExpensesByBrandService;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_200_when_valid_request_with_content() throws Exception {
        List<TopExpensesByBrand> topExpensesByBrands = new ArrayList<>();
        topExpensesByBrands.add(TopExpensesByBrand.builder()
                .brand(Brand.VISA)
                .amount(BigDecimal.valueOf(3516554.58))
                .client("client10")
                .build());
        topExpensesByBrands.add(TopExpensesByBrand.builder()
                .brand(Brand.MASTERCARD)
                .amount(BigDecimal.valueOf(455444444.50))
                .client("client11")
                .build());

        when(this.topExpensesByBrandService.getTopExpensesByBrand(any(LocalDateTime.class),any(LocalDateTime.class)))
                .thenReturn(topExpensesByBrands);

        this.mockMvc.perform(get("/client").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].operadora").value(topExpensesByBrands.get(0).getBrand().toString().toLowerCase(Locale.ROOT)))
                .andExpect(jsonPath("$[0].valorTotal").value(topExpensesByBrands.get(0).getAmount()))
                .andExpect(jsonPath("$[0].cliente").value(topExpensesByBrands.get(0).getClient()));
    }

    @Test
    public void should_return_200_when_valid_request_without_content() throws Exception {
        List<TopExpensesByBrand> topExpensesByBrands = new ArrayList<>();

        when(this.topExpensesByBrandService.getTopExpensesByBrand(any(LocalDateTime.class),any(LocalDateTime.class)))
                .thenReturn(topExpensesByBrands);

        this.mockMvc.perform(get("/client").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(topExpensesByBrands));
    }

}
