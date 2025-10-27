package ca.mcgill.ecse428.letterbook.service;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates a user by email and password and returns a JWT token on
     * success.
     * 
     * @param email    user email
     * @param password plain-text password
     * @return Map with "token" and "email" keys
     */
    @Transactional(readOnly = true)
    public Map<String, String> login(String email, String password) {
        try {
            // authenticate via Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            // find user
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

            // issue JWT
            String token = jwtUtil.generateToken(email);

            return Map.of("token", token, "email", user.getEmail());

        } catch (AuthenticationException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }
}
