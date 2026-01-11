package com.ticketsystem.security.auth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET = "dev-secret-change-later";

    public static Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }
}
