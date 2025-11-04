@develop
Feature: Create new Collection
  As a user
  I would like to create new collections
  so that I can quickly save it for future reference

  Background:
    Given the following users exist in the System:
      | Email          | Password     |
      | user@gmail.com | Password123! |
    And the collection "My Books" exists for the account associated with email "user@gmail.com"

  Scenario: Create New Collection (Normal Flow)
    Given the user is logged into the account with email "user@gmail.com"
    And the user has no collection "Cool Books"
    When the user attempts to create a collection "Cool Books"
    Then the collection "Cool Books" is added to the user's collections
    And message "Collection created successfully" is issued

  Scenario: Attempt to create an existing collection (Error Flow)
    Given the user is logged into the account with email "user@gmail.com"
    When the user attempts to create a collection "My Books"
    Then message "This collection already exists" is issued
    And no collection is created

  Scenario: Attempt to create collection with duplicate name case insensitive (Error Flow)
    Given the user is logged into the account with email "user@gmail.com"
    When the user attempts to create a collection "my books"
    Then message "This collection already exists" is issued
    And no collection is created

  Scenario: Attempt to create collection with empty name (Error Flow)
    Given the user is logged into the account with email "user@gmail.com"
    When the user attempts to create a collection ""
    Then message "Collection name cannot be empty" is issued
    And no collection is created

  Scenario: Attempt to create collection with whitespace-only name (Error Flow)
    Given the user is logged into the account with email "user@gmail.com"
    When the user attempts to create a collection "   "
    Then message "Collection name cannot be empty" is issued
    And no collection is created

  Scenario: Unauthenticated user attempts to create a collection (Error Flow)
    Given the user is not logged into the application
    When the user attempts to create a collection "Cool Collection"
    Then message "Authentication required. Please log in to create collections" is issued
    And no collection is created
