package br.com.erickmarques.loan_manager.builder;

import br.com.erickmarques.loan_manager.customer.CustomerRequest;

public class CustomerRequestBuilder {

    public static CustomerRequest createDefault() {
        return CustomerRequest.builder()
                .name("John Doe")
                .phone("123456789")
                .notes("Test notes")
                .build();
    }
}
