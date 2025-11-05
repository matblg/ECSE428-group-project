@develop
Feature: Create new Collection
  As a user
  I would like to create new collections
  so that I can quickly save it for future reference

  Background:
    Given the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the collection "My Books" exists for the user "user"

  Scenario: Create New Collection (Normal Flow)
    Given the user "user" is logged into the application
    And the user has no collection "Cool Books"
    When the user attempts to create a collection with name "Cool Books"
    Then the collection "Cool Books" is added to the user's collections
    And message "Collection created successfully" is issued

  Scenario: Attempt to create an existing collection (Error Flow)
    Given the user "user" is logged into the application
    When the user attempts to create a collection with name "My Books"
    Then message "This collection already exists" is issued
    And no collection is created

  Scenario: Attempt to create collection with an empty name (Error Flow)
    Given the user "user" is logged into the application
    When the user attempts to create a collection with name <Name>
    Then message "Collection name cannot be empty" is issued
    And no collection is created

    Examples:
      | Name |
      | ""   |
      | " "  |

  Scenario: Unauthenticated user attempts to create a collection (Error Flow)
    Given the user is not logged into the application
    When the user attempts to create a collection with name "Cool Collection"
    Then message "Authentication required. Please log in to create collections" is issued
    And no collection is created
