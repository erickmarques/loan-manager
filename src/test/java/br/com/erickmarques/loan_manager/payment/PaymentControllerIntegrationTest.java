package br.com.erickmarques.loan_manager.payment;

import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import br.com.erickmarques.loan_manager.builder.LoanRequestBuilder;
import br.com.erickmarques.loan_manager.builder.PaymentRequestBuilder;
import br.com.erickmarques.loan_manager.customer.CustomerResponse;
import br.com.erickmarques.loan_manager.loan.LoanResponse;
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
class PaymentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID customerId;
    private UUID loanId;

    private static final String PATH = "/api/v1/payments";
    private static final String PATH_ID = PATH + "/{id}";

    @BeforeEach
    void setUp() throws Exception {
        // Create Customer
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

        // Create Loan
        var loanRequest = LoanRequestBuilder.createWithCostumer(customerId);

        var loanResult = mockMvc.perform(post("/api/v1/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var loanResponse = objectMapper.readValue(
                loanResult.getResponse().getContentAsString(),
                LoanResponse.class
        );

        loanId = loanResponse.id();
    }

    @Test
    void shouldCreatePaymentSuccessfully() throws Exception {
        var request = PaymentRequestBuilder.createWithLoan(loanId);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.loanId").value(request.loanId().toString()))
                .andExpect(jsonPath("$.amount").value(request.amount().doubleValue()));
    }

    @Test
    void shouldGetPaymentByIdSuccessfully() throws Exception {
        var createRequest = PaymentRequestBuilder.createWithLoan(loanId);

        var created = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(
                created.getResponse().getContentAsString(),
                PaymentResponse.class
        );

        mockMvc.perform(get(PATH_ID, response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.amount").value(response.amount().doubleValue()));
    }

    @Test
    void shouldUpdatePaymentSuccessfully() throws Exception {
        var createRequest = PaymentRequestBuilder.createWithLoan(loanId);

        var created = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var existing = objectMapper.readValue(
                created.getResponse().getContentAsString(),
                PaymentResponse.class
        );

        var updateRequest = PaymentRequest.builder()
                .paymentDate(createRequest.paymentDate())
                .amount(createRequest.amount().add(createRequest.amount()))
                .loanId(createRequest.loanId())
                .notes("updated")
                .type(PaymentType.FINISHED)
                .build();

        mockMvc.perform(put(PATH_ID, existing.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(updateRequest.amount().doubleValue()));
    }

    @Test
    void shouldGetAllPaymentsSuccessfully() throws Exception {
        var request = PaymentRequestBuilder.createWithLoan(loanId);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetAllPaymentsByLoanIdSuccessfully() throws Exception {
        var request = PaymentRequestBuilder.createWithLoan(loanId);

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get(PATH + "/loan/{loanId}", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].loanId").value(loanId.toString()));
    }

    @Test
    void shouldDeletePaymentSuccessfully() throws Exception {
        var request = PaymentRequestBuilder.createWithLoan(loanId);

        var result = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                PaymentResponse.class
        );

        mockMvc.perform(delete(PATH_ID, response.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(PATH_ID, response.id()))
                .andExpect(status().isNotFound());
    }
}