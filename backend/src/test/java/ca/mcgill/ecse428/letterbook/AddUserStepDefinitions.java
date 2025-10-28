package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.service.AuthService;
import ca.mcgill.ecse428.letterbook.service.UserService;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddUserStepDefinitions {

    @Autowired
    private UserRepository userRepository;

    // state captured by steps (per-scenario)
    private String currentEmail;
    private String currentPassword;
    private String lastMessage;
    private boolean loggedIn;
    private String loggedInEmail;
    @Autowired
    private UserService userService;
    @Autowired
    private TestContext ctx;
    @Autowired
    private AuthService authService;

    @Before
    public void beforeScenario() {
        userRepository.deleteAll();
        currentEmail = null;
        currentPassword = null;
        ctx.lastMessage = null;
    }

    @When("the user enters an email {string}")
    public void user_enters_email(String email) {
        this.currentEmail = email;
    }

    @When("the user enters a password {string}")
    public void user_enters_password(String password) {
        this.currentPassword = password;
    }

    @When("the user submits the registration form")
    public void user_submits_registration_form() {
        try {
            userService.createUser("hello", currentEmail, currentPassword, "");
            Map<String, String> loginResult = authService.login(currentEmail, currentPassword);

            ctx.loggedIn = true;
            ctx.loggedInEmail = currentEmail;
            userRepository.findByEmail(currentEmail)
                    .ifPresent(u -> ctx.loggedInUsername = u.getUsername());
            ctx.jwtToken = loginResult.get("token");

        } catch (IllegalArgumentException e) {
            ctx.lastMessage = e.getMessage();
        }
    }

    @Then("a new account is created with a unique user id and the email address {string}")
    public void a_new_account_is_created_with_email(String email) {
        assertTrue(userRepository.findByEmail(email).isPresent());
    }

    @Given("the account with email {string} exist in the system")
    public void the_account_with_email_exists(String email) {
        try {
            userService.createUser("hello123", email, "Password123!", "");
        } catch (IllegalArgumentException ignoreIfAlreadyExists) {
        }
    }
}
