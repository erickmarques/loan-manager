package br.com.erickmarques.loan_manager.secutiry;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String token;
    private String type;
    private Long expiresIn;
    private LocalDateTime issuedAt;
}