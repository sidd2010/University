package com.example.user.controller;
import com.example.user.model.AuthenticationRequest;
import com.example.user.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebFluxTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = {
                ReactiveUserDetailsServiceAutoConfiguration.class,
                ReactiveSecurityAutoConfiguration.class
        }
)

class AuthControllerTest {

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private WebTestClient client;

    @Test
    void testFindByUsername() {

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                "test",
                "password",
                AuthorityUtils.createAuthorityList("ROLE_USER")
        );

        when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mono.just(usernamePasswordAuthenticationToken));
        when(this.tokenProvider.createToken(any(Authentication.class))).thenReturn("atesttoken");

        var req = new AuthenticationRequest("test", "password");

        this.client.post()
                .uri("/auth/login")
                .body(BodyInserters.fromValue(req))
                .exchange()
                .expectHeader().valueEquals(HttpHeaders.AUTHORIZATION, "Beareratesttoken")
                .expectBody()
                .jsonPath("$.access_token").isEqualTo("atesttoken");

        verify(this.authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(this.tokenProvider, times(1)).createToken(any(Authentication.class));
        verifyNoMoreInteractions(this.authenticationManager);
        verifyNoMoreInteractions(this.tokenProvider);
    }

}

