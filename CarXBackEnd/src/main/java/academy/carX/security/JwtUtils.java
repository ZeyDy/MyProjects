package academy.carX.security;

import academy.carX.dto.UserDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for JWT operations such as generating, parsing, and validating JWTs.
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${carX.app.jwtSecret}")
    private String jwtSecret;

    @Value("${carX.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Set the secret key used for signing JWT.
     * @param key The secret key as a string.
     */
    public void setSecretKey(String key) {
        jwtSecret = key;
    }

    /**
     * Set the expiration time in milliseconds for JWT.
     * @param ms Expiration time in milliseconds.
     */
    public void setExpirationMs(int ms) {
        jwtExpirationMs = ms;
    }

    /**
     * Generates a JWT token for the authenticated user.
     * @param authentication The authentication object containing the user's principal and credentials.
     * @return A JWT string.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDto userPrincipal = (UserDto) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    /**
     * Converts a base64 encoded string secret to a Key object.
     * @param secret The secret key as a base64 encoded string.
     * @return A Key object.
     */
    public Key toKey(String secret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private Key key() {
        return toKey(jwtSecret);
    }

    /**
     * Extracts the username from a JWT token.
     * @param token The JWT token string.
     * @return The username from the JWT token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Validates a JWT token by parsing and checking its expiration, format, and signature.
     * @param authToken The JWT token to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
