package br.com.erickmarques.loan_manager.secutiry;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> getToken(@Valid @RequestBody TokenRequestDTO dto) {
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authenticate.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}