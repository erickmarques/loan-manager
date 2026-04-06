package br.com.erickmarques.loan_manager.secutiry;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterToken extends OncePerRequestFilter {

    private final TokenServiceImpl tokenServiceImpl;
    private final UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null && !authorizationHeader.isEmpty()) {
            var token = authorizationHeader.replace("Bearer ", "");
            var subject = this.tokenServiceImpl.getSubject(token);

            User user = this.repository.findByLogin(subject)
                                .orElseThrow(() -> new UsernameNotFoundException("Login/Senha incorretos!"));

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}