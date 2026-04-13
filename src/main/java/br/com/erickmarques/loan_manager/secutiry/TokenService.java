package br.com.erickmarques.loan_manager.secutiry;

public interface TokenService {
    TokenResponseDTO generateToken(User user);
    String getSubject(String token);
}
