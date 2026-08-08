package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsTest {


    @Test
    void createTokenTest() {

        JwtUtils.init("cinema-movie-ticket-system-jwt-secret-key-2026",604800000L);

        String token = JwtUtils.createToken(3,"admin");
        System.out.println(token);

    }
}