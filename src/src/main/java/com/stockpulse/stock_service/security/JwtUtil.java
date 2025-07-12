package com.stockpulse.stock_service.security;

import com.stockpulse.stock_service.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
    import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {



private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    private final long EXPIRATION = 86400000; // 1 day

    public String generateToken(User user) {
        return Jwts.builder()
    .setSubject(user.getUsername())
    .claim("role", user.getRole().name())
    .setIssuedAt(new Date())
    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
    .signWith(SECRET_KEY)
    .compact();

    }

    public String extractUsername(String token) {
         return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)   // 🔥 Exception occurs here
            .getBody()
            .getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
