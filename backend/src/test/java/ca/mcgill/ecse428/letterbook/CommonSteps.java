package ca.mcgill.ecse428.letterbook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;   // @Given, @When, @Then, @And

public class CommonSteps {

    @Autowired private TestContext ctx;
    @Autowired private UserRepository userRepository;

    @Before
    public void resetScenario() {
        ctx.lastMessage = null;
        userRepository.deleteAll();
    }

    @Then("the user is logged into the account with email {string}")
    public void the_user_is_logged_into_account_with_email(String email) {
        ctx.loggedIn = true;
        ctx.loggedInEmail = email;
    }

    @Given("the user is not logged into the application")
    public void the_user_is_not_logged_into_the_application() {
        ctx.loggedIn = false;
        ctx.loggedInEmail = null;
        ctx.loggedInUsername = null;
    }

    @Then("message {string} is issued")
    public void message_is_issued(String expected) {
        assertEquals(expected, ctx.lastMessage);
    }


    @Given("a new user is on the registration page")
    public void a_new_user_is_on_the_registration_page() {
    }
}
