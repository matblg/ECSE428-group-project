Feature: Add Book to Collection
  As a user
  I would like to add a book to my collection by searching for it
  So that I can track and organize books I own or want to read

  Scenario: Add a book to an existing collection (Normal Flow)
    Given the user is logged into the application
    And the collection "My Books" exists for the account
    And the collection "My Books" is empty
    When the user attempts to add "To Kill a Mockingbird"
    And the user chooses the "My Books" collection
    Then the book "To Kill a Mockingbird" is added to the "My Books" collection

  Scenario: Add a book to a new collection (Alternative Flow)
    Given the user is logged into the application
    And the collection "My Books" exists for the account
    When the user attempts to add "1984" to a collection
    And the user chooses to add a new collection with name "Classics"
    Then the book "1984" is added to the "Classics" collection

  Scenario: Attempt to add book already in collection (Error Flow)
    Given the user is logged into the application
    And the collection "My Books" exists for the account
    And the book "To Kill a Mockingbird" is in the "My Books" collection
    When the user attempts to add "To Kill a Mockingbird" to a collection
    And the user chooses the "My Books" collection
    Then message "This book is already in your collection" is issued
    And no book is added to the collection

  Scenario: Unauthenticated user attempts to add book to collection (Error Flow)
    Given the user is not logged into the application
    When the user attempts to add "To Kill a Mockingbird" to a collection
    Then message "Authentication required. Please log in to add books to your collection" is issued
