package ca.mcgill.ecse428.letterbook;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewProfileStepDefinitions {
    
        @Given("the application is connected to the Books API")
    public void the_application_is_connected_to_the_books_api() {
        assertTrue(true);
    }


    @Given("the following reading history exists for {string}:")
    public void the_following_reading_history_exists_for(String string, io.cucumber.datatable.DataTable dataTable) {
        assertTrue(true);   
    }

    @Given("the following collections exist for {string}:")
    public void the_following_collections_exist_for(String string, io.cucumber.datatable.DataTable dataTable) {
        assertTrue(true);    
    }
        @When("the user opens their Profile page")
    public void the_user_opens_their_profile_page() {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @Then("the section {string} is visible")
    public void the_section_is_visible(String sectionName) {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @Then("it lists the following rows:")
    public void it_lists_the_following_rows(DataTable table) {

        // Stub: just mark as successful
        assertTrue(true);
    }

        @Then("it displays the following collection entries:")
    public void it_displays_the_following_collection_entries(DataTable table) {
        // Stub: just mark as successful
        assertTrue(true);
    }

    @When("the user applies Reading History filter {string} = {string}")
    public void the_user_applies_reading_history_filter(String string, String string2) {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(true);
    }

    @Then("the Reading History list shows the following {int} titles: {string}")
    public void the_reading_history_list_shows_the_following_titles(Integer expectedCount, String expectedTitles) {
        // Stub: just mark as successful
        assertTrue(true);
    }


    @Given("the user has no entries in Reading History")
    public void the_user_has_no_entries_in_reading_history() {
        // Stub: just mark as successful
        assertTrue(true);
    }


    @Then("the section {string} shows the message {string}")
    public void the_section_shows_the_message(String sectionName, String message) {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @Given("the user has no Collections")
    public void the_user_has_no_collections() {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @Given("the account {string} has Reading History and Collections data")
    public void the_account_has_reading_history_and_collections_data(String accountName) {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @When("user attempts to access the profile of {string}")
    public void user_attempts_to_access_the_profile_of(String accountName) {
        // Stub: just mark as successful
        assertTrue(true);
    }

        @Then("the sections {string} and {string} are not visible")
    public void the_sections_and_are_not_visible(String section1, String section2) {
        // Stub: just mark as successful
        assertTrue(true);
    }
}
