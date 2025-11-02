Feature: ID002 Login User
  As a user
  I want to log into my account
  So that I can securely access my personal Letterbook features and data

  Background:
    Given the following users exist in the System:
      | username | email          | bio                   | password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |
    And the user is not logged into the application

  Scenario: Login User With Username (Normal Flow)
    When the user tries to login with email "user@gmail.com" and password "Password123!"
    Then the user is logged into the account with email "user@gmail.com"

  Scenario: User Doesn't exist (Error Flow)
    When the user tries to login with email "user1@gmail.com" and password "Password123!"
    Then message "No existing account is associated with that email" is issued
    And the user is still not logged into the application

  Scenario: Incorrect Password Entered (Error Flow)
    When the user tries to login with email "user@gmail.com" and password "Password321!"
    Then message "Incorrect password" is issued
    And the user is still not logged into the application
