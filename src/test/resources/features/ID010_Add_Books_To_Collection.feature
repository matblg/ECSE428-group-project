Feature: Add Book to Collection
  As a user
  I would like to add a book to my collection
  so that I can quickly save it for future reference

  Background:
    Given the following users exist in the System:
      | Email          | Password     |
      | user@gmail.com | Password123! |
    And the collection "My Books" exists for the account associated with email "user@gmail.com"

  Scenario: Add a book to an existing collection (Normal Flow)
    Given the user is logged into the account with email "user@gmail.com"
    And the user's "My Books" collection is empty
    When the user attempts to add "To Kill a Mockingbird"
    And the user chooses the "My Books" collection
    Then the book "To Kill a Mockingbird" is added to the user's "My Books" collection

  Scenario: Add a book to a new collection (Alternative Flow)
    Given the user is logged into the account with email "user@gmail.com"
    When the user attempts to add "1984" to a collection
    And the user chooses to add a new collection with name "Classics"
    Then the collection "Classics" is added to the user's collections
    And the book "1984" is added to the user's "Classics" collection

  Scenario: Attempt to add book already in collection (Error Flow)
    Given the user is logged into the account with email "user@gmail.com"
    And the book "Frankenstein" is in the user's "My Books" collection
    When the user attempts to add "Frankenstein" to a collection
    And the user chooses the "My Books" collection
    Then message "This book is already in your collection" is issued
    And no book is added to the collection

  Scenario: Unauthenticated user attempts to add book to collection (Error Flow)
    Given the user is not logged into the application
    When the user attempts to add "To Kill a Mockingbird" to a collection
    Then message "Authentication required. Please log in to add books to your collection" is issued
