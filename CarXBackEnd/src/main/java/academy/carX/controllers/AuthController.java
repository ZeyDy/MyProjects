package academy.carX.controllers;

import academy.carX.payload.requests.LoginRequest;
import academy.carX.payload.responses.JwtResponse;
import academy.carX.payload.responses.MessageResponse;
import academy.carX.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import academy.carX.payload.requests.SignupRequest;

/**
 * Controller class for handling authentication requests such as sign-ins and sign-ups.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService authService;

    /**
     * Constructs an AuthController with the specified AuthService.
     * @param authService The authentication service to handle authentication logic.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user based on login credentials.
     * @param loginRequest The request containing the user's credentials.
     * @return ResponseEntity containing a JwtResponse if authentication is successful.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    /**
     * Registers a new user with the provided sign-up details.
     * @param signUpRequest The sign-up request containing user details.
     * @return ResponseEntity containing a MessageResponse indicating success or failure.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        try {
            MessageResponse response = authService.registerUser(signUpRequest);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException e) {
            logger.error("Registration failed: {}", e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(new MessageResponse(e.getReason()));
        }
    }
}
