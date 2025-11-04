@develop
Feature: Mark Book as Currently Reading
  As a user 
  I want to mark a books that I am currently reading
  So that I can track my ongoing reading progress

  Background:
    Given the following books exist in the catalogue:
      | Title            | Author            |
      | The Way of Kings | Brandon Sanderson |
      | Dune             | Frank Herbert     |
    And the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the user "user" is logged into the application

  Scenario: Mark a book as currently reading (Normal Flow)
    Given the user "user" has no books in their Reading History
    When the user marks "The Way of Kings" as currently reading
    Then the book "The Way of Kings" appears in the Reading History with status "STARTED"

  Scenario: Mark another book while already reading one (Alternative Flow)
    Given the user has "Dune" marked as currently reading
    When the user marks "The Way of Kings" as currently reading
    Then "Dune" remains in Reading History with status "STARTED"
    And "The Way of Kings" is added with status "STARTED"

  Scenario: Attempt to mark a book already completed as currently reading (Error Flow)
    Given the user has "Dune" in Reading History with status "COMPLETED"
    When the user marks "Dune" as currently reading
    Then message "This book has already been completed" is issued

  Scenario: Attempt to mark a book already marked as currently reading (Error Flow)
    Given the user has "The Way of Kings" marked as currently reading
    When the user marks "The Way of Kings" as currently reading
    Then message "This book is already marked as currently reading" is issued

  Scenario: Mark a book as currently reading that does not exist (Error Flow)
    When the user marks "Nonexistent Book" as currently reading
    Then message "This book does not exist in the catalogue" is issued
