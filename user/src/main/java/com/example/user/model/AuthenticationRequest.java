package com.example.user.model;


import javax.validation.constraints.NotBlank;

public record AuthenticationRequest(
        @NotBlank String username ,
        @NotBlank String password) {
}
