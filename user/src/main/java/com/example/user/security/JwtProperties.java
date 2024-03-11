package com.example.user.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
@Data
@Component
public class JwtProperties {
    private String secretKey="rzxlszyykpbgqcflzxsqcysyhljt";
    private final long validityInMs=3600000;//1h
}