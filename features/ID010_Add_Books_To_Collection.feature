Feature: Add Book to Collection
  As a user
  I would like to add a book to my collection by searching for it
  So that I can track and organize books I own or want to read

  Scenario: Add a book to collection by ISBN (Normal Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching ISBN "9780061120084"
    And the search returns the book "To Kill a Mockingbird" by "Harper Lee"
    And selecting the book "To Kill a Mockingbird"
    Then the book "To Kill a Mockingbird" is added to my collection
    And a "Book added to collection successfully" message is issued

  Scenario: Add a book to collection by title (Normal Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching title "1984"
    And the search returns the book "1984" by "George Orwell"
    And selecting the book "1984"
    Then the book "1984" is added to my collection
    And a "Book added to collection successfully" message is issued

  Scenario: Add a book to collection by author (Normal Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching author "Harper Lee"
    And the search returns multiple books by "Harper Lee"
    And selecting the book "To Kill a Mockingbird"
    Then the book "To Kill a Mockingbird" is added to my collection
    And a "Book added to collection successfully" message is issued

  Scenario Outline: Add books by searching different criteria (Normal Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching <SearchType> "<SearchTerm>"
    And the search returns the book "<Title>" by "<Author>"
    And selecting the book "<Title>"
    Then the book "<Title>" is added to my collection
    And a "Book added to collection successfully" message is issued

    Examples:
      | SearchType | SearchTerm        | Title                  | Author              |
      | ISBN       | 9780451524935     | 1984                   | George Orwell       |
      | title      | The Great Gatsby  | The Great Gatsby       | F. Scott Fitzgerald |
      | author     | J.R.R. Tolkien    | The Lord of the Rings  | J.R.R. Tolkien      |
      | genre      | Science Fiction   | Foundation             | Isaac Asimov        |

  Scenario: Add book with multiple search results (Alternative Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching title "The Lord"
    And the search returns multiple matching books
    And a list of results is displayed with titles and authors
    And selecting the book "The Lord of the Rings" by "J.R.R. Tolkien"
    Then the book "The Lord of the Rings" is added to my collection
    And a "Book added to collection successfully" message is issued

  Scenario: Add book by partial title match (Alternative Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching title "Great Gats"
    And the search returns the book "The Great Gatsby" by "F. Scott Fitzgerald"
    And selecting the book "The Great Gatsby"
    Then the book "The Great Gatsby" is added to my collection
    And a "Book added to collection successfully" message is issued

  Scenario: Search for book but do not add to collection (Alternative Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching ISBN "9780544003415"
    And the search returns the book "The Hobbit" by "J.R.R. Tolkien"
    And canceling the addition
    Then no book is added to my collection

  Scenario: Attempt to add book already in collection (Error Flow)
    Given I am logged into the reading app
    And the book "To Kill a Mockingbird" is already in my collection
    When requesting addition of book by searching ISBN "9780061120084"
    And the search returns the book "To Kill a Mockingbird" by "Harper Lee"
    And selecting the book "To Kill a Mockingbird"
    Then a "This book is already in your collection" error message is issued
    And no duplicate book is added to my collection

  Scenario: Search returns no results (Error Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching ISBN "9999999999999"
    And the search returns no results
    Then a "No books found matching your search" message is issued
    And no book is added to my collection

  Scenario: Search with non-existent book title (Error Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching title "XyZabc123NonexistentBook456"
    And the search returns no results
    Then a "No books found matching your search" message is issued
    And no book is added to my collection

  Scenario Outline: Attempt to add book with invalid ISBN format (Error Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching ISBN "<InvalidISBN>"
    Then an "Invalid ISBN format. Please enter a valid 10 or 13 digit ISBN" error message is issued
    And no search is performed

    Examples:
      | InvalidISBN   |
      | 123           |
      | abc123def456  |
      | 978-invalid   |
      | 12345         |

  Scenario: Attempt to search without entering search criteria (Error Flow)
    Given I am logged into the reading app
    When requesting addition of book without entering search criteria
    Then a "Please enter a search term (ISBN, title, author, or genre)" error message is issued
    And no search is performed

  Scenario: Search service is unavailable (Error Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching ISBN "9780061120084"
    And the search service is unavailable
    Then a "Service temporarily unavailable. Please try again later" error message is issued
    And no book is added to my collection

  Scenario: Unauthenticated user attempts to add book to collection (Error Flow)
    Given I am not logged into the reading app
    When requesting addition of book by searching ISBN "9780061120084"
    Then an "Authentication required. Please log in to add books to your collection" error message is issued
    And no search is performed

  Scenario: Search by genre returns multiple results (Alternative Flow)
    Given I am logged into the reading app
    When requesting addition of book by searching genre "Mystery"
    And the search returns multiple books in the "Mystery" genre
    And a list of results is displayed with titles, authors, and covers
    And selecting the book "The Da Vinci Code" by "Dan Brown"
    Then the book "The Da Vinci Code" is added to my collection
    And a "Book added to collection successfully" message is issued