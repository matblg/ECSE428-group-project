Feature: Search catalogue of books

  As a user of this application,
  I want to search books by criteria such as title, author and genre
  so that I can view this application's catalogue of books.

  Background:
    Given there exists the following book entities:
      | title                                       | author                 | genre
      | "Moby Dick"                                 | "Joe Mama"             | "Fiction"
      | "How 2 Get In FAANG"                        | "Jeffrey Bezos"        | "Fantasy"
      | "The Hunger Games"                          | "Jeffrey Bezos"        | "Fiction"
      | "The Great Gatsby's Guide to Crypto"        | "F. Scott Fitzholder"  | "Fiction"
      | "Frankenstein's Monster's Skincare Guide"   | "Joe Mama"             | "Self-help"
      | "Lord of the Flies: A Team-Building Manual" | "William Golding, MBA" | "Self-help"

  Scenario Outline: Successfully search for an existing book
    When the user searches for a book with criteria: "<title>", "<author>", "<genre>"
    Then the user should see a list of books matching the search criteria

    Examples:
      | title              | author          | genre       |
      |                    |                 | "Self-help" |
      |                    | "Jeffrey Bezos" | "Fantasy"   |
      | "The Hunger Games" |                 |             |
      | "Moby Dick         | "Joe Mama"      | "Fiction"   |

  Scenario Outline: Unsuccessfully search for a book that doesn't exist in the catalogue
    When the user searches for a book with criteria: "<title>", "<author>", "<genre>"
    Then the user should see a "No books found" message

    Examples:
      | title                 | author              | genre      |
      | "A Non-Existent Book" |                     |            |
      |                       | "An Unknown Author" |            |
      |                       |                     | "NotReal"  |
      | "Moby Dick"           | "Jeffrey Bezos"     | "Fiction"  |

