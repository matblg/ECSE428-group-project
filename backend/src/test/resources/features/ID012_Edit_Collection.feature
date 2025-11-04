Feature: ID012 Edit Collections
  As a user
  I want to rename, delete, and reorganize books within my collections
  So that my saved books stay tidy and up to date

  Background:
    Given the following users exist in the System:
      | username | email           | bio                   | password     |
      | user     | user@gmail.com  | I love fantasy novels | Password123! |
      | other    | other@gmail.com | I read everything     | Password456! |

  Scenario: Rename an existing collection
    Given the user is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title                |
      | My Books       | The Hobbit           |
      | Autumn List    | Practical DD Design  |
    When the user renames the collection "My Books" to "Favorites"
    Then the collection "Favorites" exists
    And the collection "My Books" does not exist
    And the collection "Favorites" contains the book "The Hobbit"

  Scenario: Remove a book from a collection
    Given the user is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title      |
      | My Books       | Dune       |
      | My Books       | Foundation |
    When the user removes the book "Dune" from the collection "My Books"
    Then the collection "My Books" does not contain the book "Dune"
    And the collection "My Books" contains the book "Foundation"

  Scenario: Move a book from one collection to another
    Given the user is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title      |
      | To Read        | Dune       |
      | Favorites      | Foundation |
    When the user moves the book "Dune" from the collection "To Read" to the collection "Favorites"
    Then the collection "Favorites" contains the book "Dune"
    And the collection "To Read" does not contain the book "Dune"

  Scenario: Delete a collection
    Given the user is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title  |
      | Classics       | 1984   |
      | Classics       | Emma   |
    When the user deletes the collection "Classics"
    Then the collection "Classics" does not exist

  # ---- Error Flows ----

  Scenario: Rename to a name that already exists
    Given the user is logged into the application
    And the following collections exist for "user":
      | CollectionName | Title |
      | My Books       | Dune  |
      | Favorites      | Emma  |
    When the user renames the collection "My Books" to "Favorites"
    Then message "A collection with this name already exists" is issued
    And the collection "My Books" exists
    And the collection "Favorites" exists

  Scenario: Unauthenticated user attempts to edit a collection
    Given the user is not logged into the application
    And the following collections exist for "user":
      | CollectionName | Title |
      | My Books       | Dune  |
    When the user deletes the collection "My Books"
    Then message "Authentication required. Please log in to edit your collections" is issued
    And the collection "My Books" exists

  Scenario: Prevent non-owners from editing another user's collection
    Given the user is logged into the application
    And the following collections exist for "other":
      | CollectionName | Title    |
      | Autumn List    | River    |
    And the active collection owner is "other"
    When the user deletes the collection "Autumn List"
    Then message "You are not authorized to edit this collection" is issued
    And the collection "Autumn List" exists
