package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    private static final ThreadLocal<Integer> THREAD_LOCAL_USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> THREAD_LOCAL_ROLE = new ThreadLocal<>();

    private static String secret;
    private static Long expiration;

    //从jwtconfig中获取密钥和签发时间
    public static void init(String secret, Long expiration) {
        JwtUtils.secret = secret;
        JwtUtils.expiration = expiration;
    }

    public static String createToken(Integer userId, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public static void setCurrentUser(Integer userId, String role) {
        THREAD_LOCAL_USER_ID.set(userId);
        THREAD_LOCAL_ROLE.set(role);
    }

    public static Integer getCurrentUserId() {
        return THREAD_LOCAL_USER_ID.get();
    }

    public static String getCurrentRole() {
        return THREAD_LOCAL_ROLE.get();
    }

    public static void removeCurrentUser() {
        THREAD_LOCAL_USER_ID.remove();
        THREAD_LOCAL_ROLE.remove();
    }
}
