package br.com.erickmarques.loan_manager.secutiry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtProperties jwtProperties;

    @Override
    public TokenResponse generateToken(User user) {
        var token = JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withSubject(user.getUsername())
                .withClaim("id", user.getId().toString())
                .withClaim("name", user.getName())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(jwtProperties.getExpiration() / 60000)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(jwtProperties.getSecret()));

        return TokenResponse.builder()
                .token(token)
                .type(jwtProperties.getType())
                .expiresIn(jwtProperties.getExpiration())
                .issuedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(jwtProperties.getSecret()))
                .withIssuer(jwtProperties.getIssuer())
                .build()
                .verify(token)
                .getSubject();
    }
}
