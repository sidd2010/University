package com.example.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {
    private String AUTHORITIES_KEY="roles";
    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    @PostConstruct
    public void init(){
        var secret= Base64.getEncoder()
                .encodeToString(this.jwtProperties.getSecretKey()
                        .getBytes());
        this.secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    public String createToken(Authentication authentication){
        String username=authentication.getName();
        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        var claims= Jwts.claims().setSubject(username);
        if (!authorities.isEmpty()){
            claims.put(AUTHORITIES_KEY,authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));

        }
        Date now= new Date();
        Date validity= new Date(now.getTime()+jwtProperties.getValidityInMs());
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(this.secretKey, SignatureAlgorithm.HS256).compact();
    }

    public Authentication getAuthentication(String token){
        Claims claims=Jwts.parserBuilder().setSigningKey(this.secretKey)
                .build().parseClaimsJws(token).getBody();
        Object authoritiesClaim =claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities=authoritiesClaim==null
                ? AuthorityUtils.NO_AUTHORITIES
                :AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        User principal= new User(claims.getSubject(), "",authorities);
        return new UsernamePasswordAuthenticationToken(principal,token,authorities);
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claims=Jwts.parserBuilder().setSigningKey(this.secretKey)
                    .build().parseClaimsJws(token);
            log.info("expiration date :{}",claims.getBody().getExpiration());
            return true;
        } catch (JwtException| IllegalArgumentException e){
            log.info("Invalid Jwt Token : {}",e.getMessage());
            log.info("Invalid Jwt Token trace.",e);
        }
        return false;
    }

}

