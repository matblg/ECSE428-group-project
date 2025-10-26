package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.service.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.spring.ScenarioScope;

public class AddUserStepDefinitions {
    @Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	// PasswordEncoder bean may not be provided in the app config for tests — create one locally
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // state captured by steps (per-scenario)
	private String currentEmail;
	private String currentPassword;
	private String lastMessage;
	private boolean loggedIn;
	private String loggedInEmail;


    @Before
	public void beforeScenario() {
		// clean repository to keep scenarios isolated
        currentEmail = null;
        currentPassword = null;
        lastMessage = null;
        loggedInEmail = null;
		userRepository.deleteAll();
	}

    @Given("the user is not logged into the application")
	public void the_user_is_not_logged_in() {
        loggedIn = false;
	}

    @Given("a new user is on the registration page")
	public void a_new_user_on_registration_page() {
	}

	@When("the user enters an email {string}")
	public void user_enters_email(String email) {
		this.currentEmail = email;
	}

	@When("the user enters a password {string}")
	public void the_user_enters_a_password(String password) {
		this.currentPassword = password;
	}

    @When("the user submits the registration form")
	public void the_user_submits_the_registration() {
        try {
            userService.createUser("hello", currentEmail, currentPassword, "");
        } catch (IllegalArgumentException e) {
            lastMessage = e.getMessage();
        }
	}

    @Then("a new account is created with a unique user id and the email address {string}")
	public void a_new_account_is_created(String email) {
		String cleanEmail = email;
		assertTrue(userRepository.findByEmail(cleanEmail).isPresent());
	}

    @Then("the user is logged into the account with email {string}")
	public void the_user_is_logged_in(String email) {
		
	}

    @Then("message {string} is issued")
	public void message_is_issued(String expected) {
		assertEquals(expected, this.lastMessage);
	}

    @Given("the account with email {string} exist in the system")
    public void the_account_exists_in_the_system(String email) {
        User user = new User("hello123", email, "", ""); 
        userRepository.save(user);
    }
}
