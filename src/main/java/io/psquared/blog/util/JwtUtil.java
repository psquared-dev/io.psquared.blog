package io.psquared.blog.util;

import io.jsonwebtoken.*;
import io.psquared.blog.config.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Slf4j
public class JwtUtil {
    private static String SECRET_KEY;

    public static String getRawSecretKey(){
        Environment environment = (Environment) AppContext.getBean("environment");
        return environment.getProperty("secretKey");
    }

    public static Key getSecretKey(){
        byte[] secretKeyBytes = Base64.getEncoder().encode(getRawSecretKey().getBytes());
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public static String generateToken(String username, Map<String, Object> payload){
        Claims claims = Jwts.claims()
                .setSubject(username);

        claims.putAll(payload);

        System.out.println(getRawSecretKey());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            return true;
        } catch(Exception e) {

        }
        return false;
    }
}
