Feature: ID010 Add Book to Collection
  As a user
  I would like to add a book to my collection
  so that I can quickly save it for future reference

  Background:
    Given the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the collection "My Books" exists for the user "user"

  Scenario: Add a book to an existing collection (Normal Flow)
    Given the user "user" is logged into the application
    And the user's "My Books" collection is empty
    When the user attempts to add "To Kill a Mockingbird"
    And the user chooses the "My Books" collection
    Then the collection "My Books" contains the book "To Kill a Mockingbird"

  Scenario: Attempt to add book already in collection (Error Flow)
    Given the user "user" is logged into the application
    And the book "Frankenstein" is in the user's "My Books" collection
    When the user attempts to add "Frankenstein" to a collection
    And the user chooses the "My Books" collection
    Then message "This book is already in your collection" is issued
    And no book is added to the collection "My Books"

  Scenario: Unauthenticated user attempts to add book to collection (Error Flow)
    Given the user is not logged into the application
    When the user attempts to add "To Kill a Mockingbird" to a collection
    Then message "Authentication required. Please log in to add books to your collection" is issued
