package br.com.erickmarques.loan_manager.secutiry;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secret;
    private Long expiration;
    private String issuer;
}