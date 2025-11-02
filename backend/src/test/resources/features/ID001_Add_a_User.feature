Feature: ID001 Add New User
  As a new user
  I want to set up my account
  So that I can begin interacting with the core features of the application. 

  Background:
    Given the user is not logged into the application

  Scenario: Create an account (Normal Flow)
    Given a new user is on the registration page
    When the user enters an email "reader@example.de"
    And the user enters a password "Password1234!"
    And the user submits the registration form
    Then a new account is created with a unique user id and the email address "reader@example.de"
    And the user is logged into the account with email "reader@example.de"

  Scenario Outline: Required information is missing (Error flow)
    Given a new user is on the registration page
    When the user enters an email "<email>"
    And the user enters a password "<password>"
    And the user submits the registration form
    Then message "<error_message>" is issued

    Examples:
      | email        | password | error_message             |
      |              | pass1234 | Email address is required |
      | reader@x.com |          | Password is required      |

  Scenario Outline: An invalid email format is entered (Error flow)
    Given the account with email "reader@example.de" exist in the system
    And a new user is on the registration page
    When the user enters an email "<email>"
    And the user enters a password "Password123!"
    And the user submits the registration form
    Then message "<message>" is issued

    Examples:
      | email             | message                                     |
      | reader@example.de | Email is already associated with an account |
      | example.de        | Invalid email format                        |

  Scenario Outline: An invalid password is entered (Error flow)
    Given a new user is on the registration page
    When the user enters an email "reader@example.de"
    And the user enters a password "<password>"
    And the user submits the registration form
    Then message "<message>" is issued

    Examples:
      | password    | message                                      |
      | passwor     | Password must contain at least 8 characters  |
      | password    | Password must contain an uppercase character |
      | PASSWORD    | Password must contain a lowercase character  |
      | Password    | Password must contain a number               |
      | Password123 | Password must contain a special character    |
