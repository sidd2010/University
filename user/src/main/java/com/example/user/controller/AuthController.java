package com.example.user.controller;

import com.example.user.model.AuthenticationRequest;
import com.example.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;
    @PostMapping("/login")
    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<AuthenticationRequest> authRequest){
        return authRequest
                .flatMap(login-> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.username(),login.password()))
                        .map(this.tokenProvider::createToken))
                .map(jwt->{
                    HttpHeaders httpHeaders=new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer"+jwt);
                    var tokenBody= Map.of("access_token",jwt);
                    return new ResponseEntity<>(tokenBody,httpHeaders, HttpStatus.OK);
                });
    }
}
