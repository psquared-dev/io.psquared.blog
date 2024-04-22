package io.psquared.blog.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.psquared.blog.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(null != authentication){
            SecretKey secretKey = Keys.hmacShaKeyFor(JwtUtil.getRawSecretKey().getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("psquared").setSubject(authentication.getName())
                    .claim("username", authentication.getName())
                    .claim("authorities", authentication.getAuthorities().stream().map(a -> a.getAuthority()).toArray(String[]::new))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                    .signWith(secretKey).compact();
            response.setHeader("Authorization", jwt);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * this will execute whenever
     * the condition evaluates to false
     */

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)  {
        return !request.getServletPath().equals("/user/login");
    }
}
