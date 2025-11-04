@develop
Feature: Manage Favorites
  As a user 
  I want to mark and manage up to three favorite books 
  So that my favorite books are displayed on my profile

  Background:
    Given the following books exist in the catalogue:
      | Title               | Author         |
      | Dune                | Frank Herbert  |
      | The Hobbit          | J.R.R. Tolkien |
      | Pride and Prejudice | Jane Austen    |
      | Foundation          | Isaac Asimov   |
    And the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the user "user" is logged into the application

  Scenario: Add a favorite book (Normal Flow)
    Given the user has no books in their Favourites
    When the user marks "Dune" as a favorite
    Then "Dune" appears in the Favorites section on the user's profile

  Scenario: Add multiple favorites up to limit (Normal Flow)
    Given the user has no books in their Favourites
    When the user marks the following books as favorites:
      | Title               |
      | Dune                |
      | The Hobbit          |
      | Pride and Prejudice |
    Then all three books appear in the Favorites section on the user's profile
    And the section displays exactly 3 books

  Scenario: Remove a favorite (Alternative Flow)
    Given "The Hobbit" is in the user's Favorites section
    When the user removes "The Hobbit" from Favorites
    Then "The Hobbit" no longer appears in the Favorites section

  Scenario: Attempt to add more than 3 favorites (Error Flow)
    Given the user's Favorites section contains the following books:
      | Title               |
      | Dune                |
      | The Hobbit          |
      | Pride and Prejudice |
    When the user marks "Foundation" as a favorite
    Then message "You can only have up to 3 favorite books" is issued

  Scenario: Attempt to add a book already marked as favorite (Error Flow)
    Given the book "Dune" is in the user's Favorites section
    When the user marks "Dune" as a favorite again
    Then message "This book is already in your favorites" is issued

  Scenario: Attempt to remove a book not in favorites (Error Flow)
    Given the user's Favorites section contains the following books:
      | Title |
      | Dune  |
    When the user removes "The Hobbit" from Favorites
    Then message "This book is not in your favorites" is issued
