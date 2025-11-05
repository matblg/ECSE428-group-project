Feature: ID012 Edit Collections
  As a user
  I want to edit (rename, delete) my collections
  So that my saved books stay tidy and up to date

  Background:
    Given the following users exist in the System:
      | username | email           | bio                   | password     |
      | user     | user@gmail.com  | I love fantasy novels | Password123! |
      | other    | other@gmail.com | I read everything     | Password456! |

  Scenario: Rename an existing collection (Normal Flow)
    Given the user "user" is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title               |
      | My Books       | The Hobbit          |
      | Autumn List    | Practical DD Design |
    When the user renames the collection "My Books" to "Favorites"
    Then the collection "Favorites" exists
    And the collection "My Books" does not exist
    And the collection "Favorites" contains the book "The Hobbit"

  Scenario: Delete a collection (Normal Flow)
    Given the user "user" is logged into the application
    And the following collections exist for "user":
    And the collection "Classics" exists for the user "user"
    When the user deletes the collection "Classics"
    Then the collection "Classics" doesn't exist for the user "user"

  Scenario: Rename a collection to a name that already exists (Error Flow)
    Given the user "user" is logged into the application
    And the following collections exist for user "user":
      | CollectionName | Title |
      | My Books       | Dune  |
      | Favorites      | Emma  |
    When the user renames the collection "My Books" to "Favorites"
    Then message "A collection with this name already exists" is issued
    And the collection "My Books" still exists for the user "user"

  Scenario: Unauthenticated user attempts to edit a collection (Error Flow)
    Given the user is not logged into the application
    And the collection "My Books" exists for the user "user"
    When the user deletes the collection "My Books"
    Then message "Authentication required. Please log in to edit your collections" is issued
    And the collection "My Books" still exists for the user "user"

  Scenario: Attempt to edit another user's collection (Error Flow)
    Given the user is logged into the application
    And the collection "Autumn List" exists for the user "other"
    And the active collection owner is "other"
    When the user deletes the collection "Autumn List"
    Then message "You are not authorized to edit this collection" is issued
    And the collection "Autumn List" still exists for the user "other"
