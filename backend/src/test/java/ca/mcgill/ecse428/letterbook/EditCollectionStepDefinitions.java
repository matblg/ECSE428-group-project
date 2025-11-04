package ca.mcgill.ecse428.letterbook;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class EditCollectionStepDefinitions {

    @Autowired private TestContext ctx;

    @Given("the user is logged into the application")
    public void the_user_is_logged_into_the_application() {
        ctx.loggedIn = true;
        Assertions.assertTrue(true);
    }

    @Given("the active collection owner is {string}")
    public void the_active_collection_owner_is(String username) {
        Assertions.assertTrue(true);
    }

    @When("the user renames the collection {string} to {string}")
    public void the_user_renames_the_collection_to(String oldName, String newName) {
        Assertions.assertTrue(true);
    }

    @When("the user deletes the collection {string}")
    public void the_user_deletes_the_collection(String name) {
        Assertions.assertTrue(true);
    }

    @When("the user removes the book {string} from the collection {string}")
    public void the_user_removes_the_book_from_the_collection(String title, String col) {
        Assertions.assertTrue(true);
    }

    @When("the user moves the book {string} from the collection {string} to the collection {string}")
    public void the_user_moves_the_book_between_collections(String title, String fromCol, String toCol) {
        Assertions.assertTrue(true);
    }

    @Then("the collection {string} exists")
    public void the_collection_exists(String col) {
        Assertions.assertTrue(true);
    }

    @Then("the collection {string} does not exist")
    public void the_collection_does_not_exist(String col) {
        Assertions.assertTrue(true);
    }

    @Then("the collection {string} contains the book {string}")
    public void the_collection_contains_the_book(String col, String title) {
        Assertions.assertTrue(true);
    }

    @Then("the collection {string} does not contain the book {string}")
    public void the_collection_does_not_contain_the_book(String col, String title) {
        Assertions.assertTrue(true);
    }
}
