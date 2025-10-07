Feature: Add New User

As a new user who want to track the books I have already
read I want to create a person account that is protected so only I can access it

# Normal flow

Scenario: A new user successfully creates an account (Normal Flow)
  Given a new user is on the registration page
  When the user enters the email address "reader@example.de"
  And the user enters the password "pass1234"
  And the user submits the registration form
  Then a new account is created with a unique user id
  And a confirmation mail is sent to the user
  And the user is redirected to his profile page

# Error flows
Scenario Outline: Mission mandatory field
  Given a new user is on the registration page
  When the user enters an email <email>
  And the user enters a password <password>
  And the user submits the registration form
  Then the system should display an error message <error_message>
  Examples:
    | email | password | error_message |
    |       | pass1234  | "Email address is required" |
    | reader@x.com |   | "Password is required       |

Scenario: Invalid email format
Given the email "reader@example.de" is already linked to another account and the user goes onto the registration page
When the user enters the email address "reader@example.de"
And the user enters the password "pass1234"
And the user submits the registration form
Then the system should display "Email already registered

Scenario: Invalid email format
Given A new user is on the registration page
When the user enters the email address "example.de"
And the user enters the password "pass1234"
And the user submits the registration form
Then the system should display "Invalid email format"

Scenario: Password too short
Given a new user is on the registration page
When the user enters the email address "reader@example.de
And the user enters the password "pass123"
And the user submits the registration form
Then the system should display "Password must be at least 8 character long"

Scenario: Server or network failure
Given A new user is on the registration page
And the user enters the email address "example.de"
And the user enters the password "pass1234"
And the user submits the registration form
When the servers are temporarily unavailable
Then the system should display "Unable to create account. Please try again later"

