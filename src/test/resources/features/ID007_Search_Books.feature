Feature: Search catalogue of books
  As a user of Letterbooked,
  I want to search books by criteria such as title, author and genre
  so that I can view this application's catalogue of books.

  Background:
    Given the application is connected to the Google Books API

  Scenario Outline: Successfully search for an existing book (Normal Flow)
    When the user searches for a book with criteria: "<title>", "<author>", "<genre>"
    Then the user should see a list of books matching the search criteria

    Examples:
      | title              | author            | genre       |
      |                    |                   | "Self-help" |
      |                    | "J.R.R. Tolkien"  | "Fantasy"   |
      | "The Hunger Games" |                   |             |
      | "Moby-Dick"        | "Herman Melville" | "Fiction"   |

  Scenario Outline: Unsuccessfully search for a book that doesn't exist in the Google Books catalogue (Error Flow)
    When the user searches for a book with criteria: "<title>", "<author>", "<genre>"
    Then the user should see a "No books found" message

    Examples:
      | title                 | author              | genre     |
      | "A Non-Existent Book" |                     |           |
      |                       | "An Unknown Author" |           |
      |                       |                     | "Mystery" |
