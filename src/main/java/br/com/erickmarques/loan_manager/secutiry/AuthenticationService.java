package br.com.erickmarques.loan_manager.secutiry;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService  {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username){
        return repository.findByLogin(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Login/Senha incorretos!"));
    }
}