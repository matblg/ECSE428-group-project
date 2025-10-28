package ca.mcgill.ecse428.letterbook;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.service.AuthService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for the "Login User" feature.
 * Handles normal and error login flows using AuthService.
 */
@SpringBootTest
public class LoginStepDefinitions {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestContext ctx;

    @Before
    public void beforeScenario() {
        userRepository.deleteAll();
        ctx.lastMessage = null;
        ctx.loggedIn = false;
        ctx.loggedInEmail = null;
        ctx.loggedInUsername = null;
        ctx.loggedInPasswordPlain = null;
    }

    // ------------------------------------------------------------------------
    // When steps
    // ------------------------------------------------------------------------

    @When("the user tries to login with email {string} and password {string}")
    public void the_user_tries_to_login_with_email_and_password(String email, String password) {
        try {
            var response = authService.login(email, password);
            ctx.loggedIn = true;
            ctx.loggedInEmail = response.get("email");
            ctx.jwtToken = response.get("token");
            ctx.lastMessage = null;
        } catch (IllegalArgumentException e) {
            ctx.loggedIn = false;
            ctx.loggedInEmail = null;
            // custom error messages based on known failures
            if (!userRepository.findByEmail(email).isPresent()) {
                ctx.lastMessage = "No existing account is associated with that email";
            } else {
                ctx.lastMessage = "Incorrect password";
            }
        }
    }

    // ------------------------------------------------------------------------
    // Then steps
    // ------------------------------------------------------------------------

    @Then("the user is logged into the account with email {string}")
    public void the_user_is_logged_into_the_account_with_email(String email) {
        assertTrue(ctx.loggedIn, "User should be logged in");
        assertEquals(email, ctx.loggedInEmail);
        assertNotNull(ctx.jwtToken, "JWT token should be generated");
    }

    @Then("the user is still not logged into the application")
    public void the_user_is_still_not_logged_into_the_application() {
        assertFalse(ctx.loggedIn, "User should not be logged in");
    }
}
