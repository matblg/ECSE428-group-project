@develop
Feature: Log Book as Read
  As a user 
  I want to log a book as read and give it a star rating
  So that I can track and reflect on what I've finished reading

  Background:
    Given the following books exist in the catalogue:
      | Title            | Author            |
      | Dune             | Frank Herbert     |
      | The Way of Kings | Brandon Sanderson |
    And the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the user "user" is logged into the application

  Scenario: Log a finished book with star rating (Normal Flow)
    Given the book "Dune" is in the Reading History with status "STARTED"
    When the user marks "Dune" as completed with rating "5"
    Then the book "Dune" has status "COMPLETED" and rating "5"
    And "Dune" appears in the user's Reading History under COMPLETED books

  Scenario: Change star rating of a completed book (Alternative Flow)
    Given "The Way of Kings" is in the Reading History with status "COMPLETED" and rating "4"
    When the user updates the rating of "The Way of Kings" to "5"
    Then the book "The Way of Kings" has status "COMPLETED" and rating "5"

  Scenario Outline: Give invalid star rating (Error Flow)
    Given "The Way of Kings" is in the Reading History with status "STARTED"
    When the user marks "The Way of Kings" as completed with rating "<Rating>"
    Then message <Message> is issued

    Examples:
      | Rating | Message                        |
      |     -1 | Rating must be between 1 and 5 |
      |      0 | Rating must be between 1 and 5 |
      |      6 | Rating must be between 1 and 5 |
      | abc    | Rating must be a number        |

  Scenario: Log a book as read that does not exist (Error Flow)
    When the user marks "Nonexistent Book" as completed with rating "4"
    Then message "This book does not exist in the catalogue" is issued
