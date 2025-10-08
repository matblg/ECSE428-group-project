Feature: Add New User
As a new user
I would like to become a user of the Letterbook System
So that I track my reading habits

  Background:
    Given the user is not logged into the application

  Scenario: Create an account (Normal Flow)
    Given a new user is on the registration page
    When the user enters the email address "reader@example.de"
    And the user enters the password "Password1234!"
    And the user submits the registration form
    Then a new account is created with a unique user id and the email address "reader@example.de"
    And a confirmation mail is sent to the user
    And the user is logged into the account with email "reader@example.de"

  Scenario Outline: Required information is missing (Error flow)
    Given a new user is on the registration page
    When the user enters an email <email>
    And the user enters a password <password>
    And the user submits the registration form
    Then message <error_message> is issued

    Examples:
      | email        | password | error_message               |
      |              | pass1234 | "Email address is required" |
      | reader@x.com |          | "Password is required"      |

  Scenario Outline: An invalid email format is entered (Error flow)
    Given the following user accounts exist in the system:
      | Email               |
      | "reader@example.de" |
    And the user is on the registration page
    When the user enters the email address <Email>
    And the user enters the password "pass1234"
    And the user submits the registration form
    Then message <Message> is issued

    Examples:
      | Email               | Password   | Message                                       |
      | "reader@example.de" | "pass1234" | "Email is already associated with an account" |
      | "example.de"        | "pass1234" | "Invalid email format"                        |

  Scenario Outline: An invalid password is entered (Error flow)
    Given a new user is on the registration page
    When the user enters the email address "reader@example.de"
    And the user enters the password <Password>
    And the user submits the registration form
    Then message <Message> is issued

    Examples:
      | Password    | Message                                      |
      | passwor     | Password must contain at least 8 charcaters  |
      | password    | Password must contain an uppercase character |
      | PASSWORD    | Password must contain a lowercase character  |
      | Password    | Password must contain a number               |
      | password123 | Password must contain a special character    |
