Feature: Add New User
  As a new User
  I would like to become a user of the application
  So that I can track my reading habits

  Background:
    Given the User is not logged into the application

  Scenario: Create an account (Normal Flow)
    Given a new User is on the registration page
    When the User enters the email address "reader@example.de"
    And the User enters the password "pass1234"
    And the User submits the registration form
    Then a new account is created with a unique user id and the email address "reader@example.de"
    And a confirmation mail is sent to the User
    And the User is logged in

  Scenario Outline: Required information is missing (Error Flow)
    Given a new User is on the registration page
    When the User enters an email <email>
    And the User enters a password <password>
    And the User submits the registration form
    Then the application should display an error message <error_message>
    Examples:
      | email        | password | error_message               |
      |              | pass1234 | "Email address is required" |
      | reader@x.com |          | "Password is required"      |

  Scenario Outline: An invalid email format is entered (Error Flow)
    Given the following user accounts exist in the application:
      | Email               |
      | "reader@example.de" |
    And the User is on the registration page
    When the User enters the email address <Email>
    And the User enters the password "pass1234"
    And the User submits the registration form
    Then message <Message> is issued
    Examples:
      | Email               | Password   | Message                                       |
      | "reader@example.de" | "pass1234" | "Email is already associated with an account" |
      | "example.de"        | "pass1234" | "Invalid email format"                        |

  Scenario Outline: An invalid password is entered (Error Flow)
    Given a new User is on the registration page
    When the User enters the email address "reader@example.de"
    And the User enters the password <Password>
    And the User submits the registration form
    Then message <Message> is issued
    Examples:
      | Password    | Message                                      |
      | passwor     | Password must contain at least 8 charcaters  |
      | password    | Password must contain an uppercase character |
      | PASSWORD    | Password must contain a lowercase character  |
      | Password    | Password must contain a number               |
      | password123 | Password must contain a special character    |