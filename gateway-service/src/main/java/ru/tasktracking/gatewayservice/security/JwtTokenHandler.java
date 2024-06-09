package ru.tasktracking.gatewayservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
public class JwtTokenHandler {

    @Value("${JwtToken.secret}")
    private String secret;

    @Value("${JwtToken.expirationDays}")
    private Integer expirationDays;

    public String generate(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setExpiration(Date.valueOf(LocalDate.now().plusDays(expirationDays)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validate(String jwt) {
        try {
            Jwts.parser().setSigningKey(secret).parse(jwt);
            return true;
        } catch (Exception ex) {
            log.error("date: " + LocalDateTime.now() + ", message: " + ex.getMessage());
        }
        log.error("The JWT token is not valid");
        return false;
    }

    public String getUserNameFromToken(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
