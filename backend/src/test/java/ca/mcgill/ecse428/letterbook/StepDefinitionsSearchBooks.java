package ca.mcgill.ecse428.letterbook;

import io.cucumber.java.en.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse428.letterbook.services.SearchService;
import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StepDefinitionsSearchBooks {

    @Autowired
    private SearchService searchService;
    private BookSearchResultDTO result;

    @When("the user searches for {string}")
    public void the_user_searches_for(String query) {
        result = searchService.searchBooks(query);
    }

    @Then("books related to the query are displayed")
    public void books_related_to_the_query_are_displayed() {
        assertNotNull(result);
        assertTrue(result.getItems() != null && !result.getItems().isEmpty());
    }
}
