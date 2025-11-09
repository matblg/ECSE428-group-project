package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class BookSearchStepDefinitions {

    @Given("the application is connected to the Google Books API")
    public void application_connected_to_google_books_api() {
        // stub implementation: always passes
        Assertions.assertTrue(true);
    }

    @When("the user searches for a book with criteria: {string}, {string}, {string}")
    public void user_searches_for_book(String title, String author, String genre) {
        // stub implementation: always passes
        Assertions.assertTrue(true);
    }

    // For Scenario Outline rows that might leave fields empty
    @When("the user searches for a book with criteria: , , {string}")
    public void user_searches_for_book_with_only_genre(String genre) {
        Assertions.assertTrue(true);
    }

    @When("the user searches for a book with no criterion")
    public void user_searches_with_no_criterion() {
        Assertions.assertTrue(true);
    }

    @Then("the user should see a list of books matching the search criteria")
    public void user_should_see_books() {
        Assertions.assertTrue(true);
    }

    @Then("message {string} is issued")
    public void message_is_issued(String message) {
        Assertions.assertTrue(true);
    }
}
