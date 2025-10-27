@ID007
Feature: Search catalogue of books
  As a user
  I want to search for books using multiple criteria such as title, author, and ISBN
  So that I can quickly discover and view books that match my interests

  Background:
    Given the application is connected to the Google Books API

  Scenario Outline: Successfully search for an existing book (Normal Flow)
    When the user searches for a book with criteria: <title>, <author>, <isbn>
    Then the user should see a list of books matching the search criteria

    Examples:
      | title          | author        | isbn            |
      | "Harry Potter" | ""            | ""              |
      | "Bible"        | ""            | ""              |
      | ""             | "Shakespeare" | ""              |
      | ""             | ""            | "9780132350884" |

  Scenario Outline: Unsuccessfully search for a book that doesn't exist in the Google Books catalogue (Error Flow)
    When the user searches for a book with criteria: <title>, <author>, <isbn>
    Then message "No books found" is issued

    Examples:
      | title                      | author              | isbn            |
      | "ZZZ Non-Existent Book XY" | ""                  | ""              |
      | ""                         | "ZZZ Unknown XYZ"   | ""              |
      | ""                         | ""                  | "0000000000000" |

  Scenario: Search for a book without inputing a search criterion (Error Flow)
    When the user searches for a book with no criterion
    Then message "Please input a search criterion" is issued
