package br.com.erickmarques.loan_manager.secutiry;

public interface TokenService {
    String generateToken(User user);
    String getSubject(String token);
}
