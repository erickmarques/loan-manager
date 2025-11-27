package br.com.erickmarques.loan_manager.customer;

import br.com.erickmarques.loan_manager.builder.CustomerRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PATH = "/api/v1/customers";

    private static final String PATH_ID = PATH + "/{id}";

    @Test
    void shouldCreateCustomerSuccessfully() throws Exception {
        var request = CustomerRequestBuilder.createDefault();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.phone").value(request.phone()));
    }

    @Test
    void shouldGetCustomerByIdSuccessfully() throws Exception {
        var createdRequest = CustomerRequestBuilder.createDefault();

        var result = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var response = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerResponse.class);

        mockMvc.perform(get(PATH + "/{id}", response.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.id").value(response.id().toString()));
    }

    @Test
    void shouldUpdateCustomerSuccessfully() throws Exception {
        var createdRequest = CustomerRequestBuilder.createDefault();

        var createResult = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var createdResponse = objectMapper.readValue(createResult.getResponse().getContentAsString(), CustomerResponse.class);

        var updateRequest = CustomerRequest.builder()
                .name("Updated Name")
                .phone("987654321")
                .notes("Updated notes")
                .build();

        mockMvc.perform(put(PATH_ID, createdResponse.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.phone").value("987654321"))
                .andExpect(jsonPath("$.notes").value("Updated notes"));
    }

    @Test
    void shouldGetAllCustomersSuccessfully() throws Exception {
        var createdRequest = CustomerRequestBuilder.createDefault();

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRequest)))
                .andExpect(status().isCreated());

        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(createdRequest.name()));
    }

    @Test
    void shouldDeleteCustomerSuccessfully() throws Exception {
        var createdRequest = CustomerRequestBuilder.createDefault();

        var createResult = mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var createdResponse = objectMapper.readValue(createResult.getResponse().getContentAsString(), CustomerResponse.class);

        mockMvc.perform(delete(PATH_ID, createdResponse.id()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(PATH_ID, createdResponse.id()))
                .andExpect(status().isNotFound());
    }
}
