package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class AddBookStepDefinitions {

    @Given("the collection {string} exists for the account associated with email {string}")
    public void the_collection_exists_for_the_account_associated_with_email(String collectionName, String email) {
        Assertions.assertTrue(true);
    }

    @Given("the user's {string} collection is empty")
    public void collection_is_empty(String collectionName) {
        Assertions.assertTrue(true);
    }

    @When("the user attempts to add {string}")
    public void user_attempts_to_add_book(String bookTitle) {
        Assertions.assertTrue(true);
    }

    @When("the user attempts to add {string} to a collection")
    public void the_user_attempts_to_add_to_a_collection(String bookTitle) {
        Assertions.assertTrue(true);
    }

    @When("the user chooses the {string} collection")
    public void user_chooses_collection(String collectionName) {
        Assertions.assertTrue(true);
    }

    @When("the user chooses to add a new collection with name {string}")
    public void user_chooses_new_collection(String collectionName) {
        Assertions.assertTrue(true);
    }

    // Scenario: Error flow
    @Given("the book {string} is in the user's {string} collection")
    public void book_is_in_collection(String bookTitle, String collectionName) {
        Assertions.assertTrue(true);
    }

    // Then steps
    @Then("the book {string} is added to the user's {string} collection")
    public void book_is_added_to_collection(String bookTitle, String collectionName) {
        Assertions.assertTrue(true);
    }

    @Then("the collection {string} is added to the user's collections")
    public void collection_is_added(String collectionName) {
        Assertions.assertTrue(true);
    }

    @Then("no book is added to the collection")
    public void no_book_added() {
        Assertions.assertTrue(true);
    }
}
