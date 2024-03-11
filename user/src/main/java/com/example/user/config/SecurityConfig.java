package com.example.user.config;

import com.example.user.repository.UserRepository;
import com.example.user.security.JwtTokenAuthenticationFilter;
import com.example.user.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtTokenProvider tokenProvider, ReactiveAuthenticationManager reactiveAuthenticationManager){
        final String PATH_UNIVERSITY = "/university/**";
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it->it
                        .pathMatchers(HttpMethod.GET,PATH_UNIVERSITY).permitAll()
                        .pathMatchers(HttpMethod.POST,PATH_UNIVERSITY).hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE,PATH_UNIVERSITY).hasRole("ADMIN")
                        .pathMatchers(PATH_UNIVERSITY).authenticated()
                        .pathMatchers("/me").authenticated()
                        .anyExchange().permitAll())
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }
    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository userRepository){
        return username -> userRepository.findById(username)
                .map(u-> User.withUsername(u.getUsername()).password(u.getPassword())
                        .authorities(u.getRoles().toArray(new String[0]))
                        .accountExpired(!u.isActive())
                        .credentialsExpired(!u.isActive())
                        .disabled(!u.isActive())
                        .accountLocked(!u.isActive())
                        .build());
    }
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService , PasswordEncoder passwordEncoder){
        var authenticationManager=new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

}
