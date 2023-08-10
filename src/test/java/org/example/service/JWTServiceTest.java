package org.example.service;

import io.jsonwebtoken.Claims;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    private User user;

    private String token;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setFirstName("Xiao");

        token = jwtService.generateToken(user);
        System.out.println(token);
    }

    @Test
    public void generateTokenTest() {
        String[] array = token.split("\\.");
        boolean bool = array.length == 3 ? true : false;
        assertTrue(bool);
    }

    @Test
    public void decryptTokenTest() {
        Claims claims = jwtService.decryptToken(token);
        String firstName = claims.getSubject();
        assertEquals(user.getFirstName(), firstName);
    }
}
