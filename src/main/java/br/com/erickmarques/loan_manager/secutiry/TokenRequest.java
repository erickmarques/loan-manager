package br.com.erickmarques.loan_manager.secutiry;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TokenRequest(

        @Email
        @NotBlank(message = "Favor informar o E-mail.")
        String email,

        @NotBlank(message = "Favor informar a senha.")
        String password
) {}