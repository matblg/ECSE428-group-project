package ca.mcgill.ecse428.letterbook;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import ca.mcgill.ecse428.letterbook.service.UserService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class ManageProfileStepDefinitions {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private TestContext ctx;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Before
    public void beforeScenario() {
        ctx.lastMessage = null;
    }

    @Given("the following users exist in the System:")
    public void the_following_users_exist_in_the_system(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> r : rows) {
            String username = r.get("username");
            String email    = r.get("email");
            String bio      = r.get("bio");
            String pwdPlain = r.get("password");

            userService.createUser(username, email, pwdPlain, bio);

            if ("user@gmail.com".equalsIgnoreCase(email)) {
                ctx.loggedIn = true;
                ctx.loggedInUsername = username;         // "user"
                ctx.loggedInEmail = email;               // "user@gmail.com"
                ctx.loggedInPasswordPlain = pwdPlain;    // "Password123!"
            }
        }
    }

    @When("requesting the modification of field {word} to value {string}")
    public void requesting_the_modification_of_field_to_value(String field, String valueParam) {
        String value = "\"\"".equals(valueParam) ? "" : valueParam;

        String newUsername = null, newEmail = null, newPassword = null, newBio = null;
        switch (field.toLowerCase()) {
            case "username": newUsername = value; break;
            case "email":    newEmail    = value; break;
            case "password": newPassword = value; break;
            case "bio":      newBio      = value; break;
            default: throw new IllegalArgumentException("Unknown field: " + field);
        }

        try {
            boolean ok = userService.updateUser(
                ctx.loggedInUsername,              // current username
                ctx.loggedInPasswordPlain,         // old password (plain)
                newUsername, newEmail, newPassword, newBio
            );

            if (ok) {
                if (newUsername != null && !newUsername.isBlank()) {
                    ctx.loggedInUsername = newUsername;
                }
                if (newEmail != null && !newEmail.isBlank()) {
                    ctx.loggedInEmail = newEmail;
                }
                if (newPassword != null && !newPassword.isBlank()) {
                    ctx.loggedInPasswordPlain = newPassword;
                }
                ctx.lastMessage = null;
            } else {
                fail("updateUser returned false (old password mismatch?)");
            }
        } catch (IllegalArgumentException ex) {
            ctx.lastMessage = ex.getMessage();
        }
    }

    @Then("the field {word} now has value {string}")
    public void the_field_now_has_value(String field, String expected) {
        var current = userRepository.findByEmail(ctx.loggedInEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + ctx.loggedInEmail));

        switch (field.toLowerCase()) {
            case "username":
                assertEquals(expected, current.getUsername());
                break;
            case "email":
                assertEquals(expected, current.getEmail());
                break;
            case "bio":
                assertEquals(expected, current.getBio());
                break;
            case "password":
                assertTrue(encoder.matches(expected, current.getPassword()),
                           "Password hash does not match expected new password");
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }

    @And("field {word} has value {string}")
    public void field_has_value(String field, String expected) {
        var current = userRepository.findByEmail(ctx.loggedInEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + ctx.loggedInEmail));

        switch (field.toLowerCase()) {
            case "username":
                assertEquals(expected, current.getUsername());
                break;
            case "email":
                assertEquals(expected, current.getEmail());
                break;
            case "bio":
                assertEquals(expected, current.getBio());
                break;
            case "password":
                assertTrue(encoder.matches(expected, current.getPassword()),
                           "Password should not have changed");
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + field);
        }
    }
}
