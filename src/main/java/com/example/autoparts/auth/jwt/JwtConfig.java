package com.example.autoparts.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtConfig {

    private String secret;

    private String issuer;

    public String getSecret() {
        return secret;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
