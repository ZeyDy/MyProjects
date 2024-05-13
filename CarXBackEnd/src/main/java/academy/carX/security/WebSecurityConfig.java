package academy.carX.security;

import academy.carX.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Security configuration class for JWT based security implementation.
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    /**
     * Creates an authentication JWT token filter bean which intercepts and processes the JWT tokens from HTTP requests.
     * @return AuthTokenFilter The JWT authentication filter.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Creates a DAO authentication provider bean configured with a user details service and a password encoder.
     * @return DaoAuthenticationProvider The DAO authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates the authentication manager bean from authentication configuration.
     * @param authConfig The authentication configuration provided by Spring Security.
     * @return AuthenticationManager The authentication manager.
     * @throws Exception if an error occurs creating the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Password encoder bean to encode and verify passwords using bcrypt hashing.
     * @return PasswordEncoder The password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security filter chain bean that configures HTTP security, specifying the rules for user authentication.
     * @param http HttpSecurity configuration built by Spring Security.
     * @return SecurityFilterChain The configured security filter chain.
     * @throws Exception if an error occurs configuring the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disable CSRF protection as it is not required for API type services.
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))  // Handle exceptions with the specified entry point.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Ensure the application does not maintain session state.
                .authorizeHttpRequests(auth ->  // Manage access control to paths.
                        auth.requestMatchers("/api/auth/**").permitAll()  // Allow public access to authentication paths.
                                .requestMatchers("/api/test/**").permitAll()  // Allow public access to test paths.
                                .anyRequest().authenticated()  // All other requests must be authenticated.
                );

        http.authenticationProvider(authenticationProvider());  // Set the custom authentication provider.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);  // Register the JWT filter.

        return http.build();
    }

}
