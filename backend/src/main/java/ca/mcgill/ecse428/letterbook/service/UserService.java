package ca.mcgill.ecse428.letterbook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(String username, String email, String password, String bio) {
        // validate username
        String cleanUsername = StringUtils.trimToNull(username);
        if (cleanUsername == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // validate email and password, get cleaned values
        String[] cleanCredentials = validateEmailAndPassword(email, password);
        String cleanEmail = cleanCredentials[0];
        String cleanPassword = cleanCredentials[1];

        // check for duplicates
        if (userRepository.findByUsername(cleanUsername).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(cleanEmail).isPresent()) {
            throw new IllegalArgumentException("Email is already associated with an account");
        }

        // create new user
        User user = new User(cleanUsername, cleanEmail, passwordEncoder.encode(cleanPassword), bio);
        userRepository.save(user);

    }

    @Transactional
    public boolean updateUser(String currentUsername,
            String oldPassword,
            String newUsername,
            String newEmail,
            String newPassword,
            String newBio) {
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + currentUsername));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        boolean updated = false;

        // username
        if (newUsername != null && !newUsername.equals(user.getUsername())) {
            String trimmed = StringUtils.trimToNull(newUsername);
            if (trimmed == null || "\"\"".equals(newUsername)) {
                throw new IllegalArgumentException("Username cannot be empty");
            }
            if (trimmed.matches(".*\\s+.*")) {
                throw new IllegalArgumentException("Username cannot contain a whitespace");
            }
            if (userRepository.findByUsername(trimmed).isPresent()) {
                throw new IllegalArgumentException("Username \"" + trimmed + "\" is already taken");
            }
            user.setUsername(trimmed);
            updated = true;
        }

        // email
        if (newEmail != null) {
            // Treat empty/whitespace-only as an error to satisfy the feature
            if (newEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }

            // Validate format (password arg is dummy; we only use the email out of it)
            String[] cleanCredentials = validateEmailAndPassword(newEmail, "TempPass123!");
            String cleanEmail = cleanCredentials[0];

            if (!cleanEmail.equals(user.getEmail())) {
                if (userRepository.findByEmail(cleanEmail).isPresent()) {
                    throw new IllegalArgumentException("An account with this email already exists");
                }
                user.setEmail(cleanEmail);
                updated = true;
            }
        }

        // password
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            String[] cleanCredentials = validateEmailAndPassword("temp@example.com", newPassword);
            String cleanPassword = cleanCredentials[1];
            if (!passwordEncoder.matches(cleanPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(cleanPassword));
                updated = true;
            }
        }

        // bio
        if (newBio != null && !newBio.equals(user.getBio())) {
            user.setBio(newBio);
            updated = true;
        }

        if (updated)
            userRepository.save(user);
        return true;
    }

    @Transactional
    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        userRepository.delete(user);
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Validates email and password format and returns cleaned values
     * 
     * @param email    - email to validate
     * @param password - password to validate
     * @return String array with [cleanEmail, cleanPassword]
     * @throws IllegalArgumentException if email or password is invalid
     */
    private String[] validateEmailAndPassword(String email, String password) {
        String cleanEmail = StringUtils.trimToNull(email);
        String cleanPassword = StringUtils.trimToNull(password);
        if (cleanEmail == null) {
            throw new IllegalArgumentException("Email address is required");
        }
        if (cleanPassword == null) {
            throw new IllegalArgumentException("Password is required");
        }
        validatePassword(cleanPassword);
        // pattern from:
        // https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(pattern);
        if (!p.matcher(cleanEmail).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new String[] { cleanEmail, cleanPassword };
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        } else if (password.toLowerCase().equals(password)) {
            throw new IllegalArgumentException("Password must contain an uppercase character");
        } else if (password.toUpperCase().equals(password)) {
            throw new IllegalArgumentException("Password must contain a lowercase character");
        } else if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain a number");
        } else if (password.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Password must contain a special character");
        }
    }

    // login

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

}
