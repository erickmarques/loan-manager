package br.com.erickmarques.loan_manager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderExample {
    public static void main(String[] args) {
        // Cria o encoder com força padrão (10)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Senha em texto puro
        String rawPassword = "#Erick2026";

        // Gera o hash
        String encodedPassword = encoder.encode(rawPassword);

        // Exibe resultado
        System.out.println("Senha original: " + rawPassword);
        System.out.println("Hash gerado: " + encodedPassword);

        // Verifica se a senha corresponde ao hash
        boolean matches = encoder.matches(rawPassword, encodedPassword);
        System.out.println("Senha confere? " + matches);
    }
}
