package br.com.erickmarques.loan_manager.loan;

import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import br.com.erickmarques.loan_manager.builder.LoanRequestBuilder;
import br.com.erickmarques.loan_manager.customer.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID customerId;

    private static final String PATH = "/api/v1/loans";
    private static final String PATH_ID = PATH + "/{id}";

    @BeforeEach
    void setUp() throws Exception {
        var customerRequest = CustomerRequestBuilder.createDefault();

        var customerResult = mockMvc.perform(post("/api/v1/customers")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(customerRequest)))
                            .andExpect(status().isCreated())
                            .andReturn();

        var customerResponse = objectMapper.readValue(
                customerResult.getResponse().getContentAsString(),
                CustomerResponse.class
        );

        customerId = customerResponse.id();
    }

    @Test
    void shouldCreateLoanSuccessfully() throws Exception {
        var request = LoanRequestBuilder.createWithCostumer(customerId);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount").value(request.amount().doubleValue()))
                .andExpect(jsonPath("$.customerName").value("John Doe"));
    }

    @Test
    void shouldGetLoanByIdSuccessfully() throws Exception {
        var createRequest = LoanRequestBuilder.createWithCostumer(customerId);

        var result = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(result.getResponse().getContentAsString(), LoanResponse.class);

        mockMvc.perform(get(PATH_ID, response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.amount").value(response.amount().doubleValue()));
    }

    @Test
    void shouldUpdateLoanSuccessfully() throws Exception {
        var createRequest = LoanRequestBuilder.createWithCostumer(customerId);

        var createResult = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var createdResponse = objectMapper.readValue(createResult.getResponse().getContentAsString(), LoanResponse.class);

        var updateRequest = LoanRequestUpdate.builder()
                .loanDate(createRequest.loanDate())
                .paymentDate(createRequest.paymentDate())
                .amount(createRequest.amount().add(createRequest.amount()))
                .percentage(createRequest.percentage())
                .totalAmountToPay(createRequest.totalAmountToPay().add(createRequest.totalAmountToPay()))
                .negotiation(true)
                .build();

        mockMvc.perform(put(PATH_ID, createdResponse.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(updateRequest.amount().doubleValue()))
                .andExpect(jsonPath("$.totalAmountToPay").value(updateRequest.totalAmountToPay().doubleValue()));
    }

    @Test
    void shouldGetAllLoansSuccessfully() throws Exception {
        var createRequest = LoanRequestBuilder.createWithCostumer(customerId);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].amount").value(createRequest.amount().doubleValue()));
    }

    @Test
    void shouldGetAllLoansByCustomerIdSuccessfully() throws Exception {
        var createRequest = LoanRequestBuilder.createWithCostumer(customerId);

        var result = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var createdResponse = objectMapper.readValue(result.getResponse().getContentAsString(), LoanResponse.class);

        mockMvc.perform(get(PATH + "/customer/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value(createdResponse.customerName()));
    }

    @Test
    void shouldDeleteLoanSuccessfully() throws Exception {
        var createRequest = LoanRequestBuilder.createWithCostumer(customerId);

        var createResult = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var createdResponse = objectMapper.readValue(createResult.getResponse().getContentAsString(), LoanResponse.class);

        mockMvc.perform(delete(PATH_ID, createdResponse.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(PATH_ID, createdResponse.id()))
                .andExpect(status().isNotFound());
    }
}
