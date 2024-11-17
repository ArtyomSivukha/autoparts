package com.example.autoparts.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.autoparts.model.enums.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final JwtConfig options;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public JwtUtil(JwtConfig options) {
        this.options = options;
    }

    public String generateToken(String email, Role role) throws IllegalArgumentException {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role.toString())
                .withIssuedAt(new Date())
                .withIssuer(this.options.getIssuer())
                .sign(Algorithm.HMAC256(this.options.getSecret()));
    }

    public String validateTokenAndRetrieveSubject(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(this.options.getSecret()))
                .withIssuer(this.options.getIssuer())
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }
}
