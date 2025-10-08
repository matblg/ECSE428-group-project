Feature: Add Book to Collection
  As a User
  I would like to add a book to my collection by searching for it
  So that I can track and organize books I own or want to read
  
  Background:
    Given the User is logged into the application
    And the application is connected to the Google Books API

  Scenario: Add a book to an existing collection (Normal Flow)
    Given the collection "My Books" exists for the account
    And the collection "My Books" is empty
    When the User attempts to add "To Kill a Mockingbird"
    And the User chooses the "My Books" collection
    Then the book "To Kill a Mockingbird" is added to the "My Books" collection

  Scenario: Add a book to a new collection (Alternative Flow)
    Given the collection "My Books" exists for the account
    When the User attempts to add "1984" to a collection
    And the User chooses to add a new collection with name "Classics"
    Then the book "1984" is added to the "Classics" collection

  Scenario: Attempt to add book already in collection (Error Flow)
    Given the collection "My Books" exists for the account
    And the book "To Kill a Mockingbird" is in the "My Books" collection
    When the User attempts to add "To Kill a Mockingbird" to a collection
    And the User chooses the "My Books" collection
    Then message "This book is already in your collection" is issued
    And no book is added to the collection