package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse428.letterbook.service.SearchService;
import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsSearchBooks {

    @Autowired
    private SearchService searchService;
    private BookSearchResultDTO result;

    @Given("the application is connected to the Google Books API")
    public void the_application_is_connected_to_the_google_books_api() {
        // This is implicit in our SearchService setup
        assertNotNull(searchService);
    }

    @When("the user searches for a book with criteria: {string}, {string}, {string}")
    public void the_user_searches_for_a_book_with_criteria(String title, String author, String isbn) {
        // Convert empty strings or null values to null
        String titleParam = (title == null || title.trim().isEmpty()) ? null : title;
        String authorParam = (author == null || author.trim().isEmpty()) ? null : author;
        String isbnParam = (isbn == null || isbn.trim().isEmpty()) ? null : isbn;

        result = searchService.searchBooks(titleParam, authorParam, isbnParam);
    }

    @When("the user searches for a book with no criterion")
    public void the_user_searches_for_a_book_with_no_criterion() {
        result = searchService.searchBooks(null, null, null);
    }

    @Then("the user should see a list of books matching the search criteria")
    public void the_user_should_see_a_list_of_books_matching_the_search_criteria() {
        assertNotNull(result);
        assertTrue(result.getItems() != null && !result.getItems().isEmpty(),
                   "Expected books to be found but result was empty");
    }

    @Then("message {string} is issued")
    public void message_is_issued(String message) {
        assertNotNull(result);
        if (message.equals("No books found")) {
            assertTrue(result.getItems() == null || result.getItems().isEmpty(),
                      "Expected no books but found some");
        } else if (message.equals("Please input a search criterion")) {
            assertTrue(result.getItems() == null || result.getItems().isEmpty(),
                      "Expected empty result when no criteria provided");
            assertEquals(0, result.getTotalItems());
        }
    }
}
