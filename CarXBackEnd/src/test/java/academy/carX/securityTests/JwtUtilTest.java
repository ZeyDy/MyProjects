package academy.carX.securityTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import academy.carX.dto.UserDto;
import academy.carX.security.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


public class JwtUtilTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;


    String testKey = "2ac6c4169e64a993ad42a141907632c40158c509c05fa21932e7f6f3a21468e3";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtils = new JwtUtils();
        jwtUtils.setSecretKey( testKey );
        jwtUtils.setExpirationMs( 1000 * 60 * 60 ); // 1 hour
    }

    @Test
    void testGenerateJwtToken() {

        UserDto user = new UserDto("user1", "user1@example.com", "pass");
        when(authentication.getPrincipal()).thenReturn(user);

        //Act
        String token = jwtUtils.generateJwtToken(authentication);
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // JWT structure check
    }

    @Test
    void testGetUserNameFromJwtToken() {
        String username = "user1";
        String token = Jwts.builder()
                .subject(username)
                .signWith( jwtUtils.toKey(testKey))
                .compact();

        assertEquals(username, jwtUtils.getUserNameFromJwtToken(token));
    }

    @Test
    void testValidateJwtToken() {
        String token = Jwts.builder()
                .subject("user1")
                .signWith(  jwtUtils.toKey(testKey) )
                .compact();

        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_Expired() {
        String token = Jwts.builder()
                .subject("user1")
                .expiration(new Date(System.currentTimeMillis() - 1000)) // Expired
                .signWith( jwtUtils.toKey(testKey))
                .compact();

        assertFalse(jwtUtils.validateJwtToken(token));
    }

}