package br.com.erickmarques.loan_manager.secutiry;

public interface TokenService {
    TokenResponse generateToken(User user);
    String getSubject(String token);
}
