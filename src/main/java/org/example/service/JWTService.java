package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String SECRET_KEY = System.getProperty("jwt.secretKey");

    private final String ISSUER = "org.example";

    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setSubject(user.getFirstName());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));


        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

        String generatedToken = builder.compact();
        return generatedToken;
    }

    public Claims decryptToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        logger.info("claims:" + claims.toString());
        return claims;
    }
}
