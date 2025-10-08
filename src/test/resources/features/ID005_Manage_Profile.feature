Feature: Manage Profile
  As a User
  I would like to edit my profile information (username, email, bio, password)
  So that my profile gets updated with the new information
  
  Background:
    Given the User is logged into the application
    And the following users exist in the application:
      | Username | Email          | Bio                   | Password     |
      | user     | user@gmail.com | I love fantasy novels | Password123! |

  Scenario Outline: Manage Profile (Normal Flow)
    When requesting the modification of field <Field> to value <UpdatedValue>
    Then the field now has value <UpdatedValue>
    Examples:
      | Field    | UpdatedValue             |
      | username | user1                    |
      | email    | user1@outlook.com        |
      | bio      | I love historical novels |
      | password | Password321#             |

  Scenario Outline: Give misformatted input to a field (Error Flow)
    When requesting the modification of field <Field> to value <InvalidInput>
    Then message <Message> is issued
    And field <Field> has value <OriginalValue>
    Examples:
      | Field    | OriginalValue  | InvalidInput  | Message                                      |
      | username | user           | some username | Username cannot contain a whitespace         |
      | username | user           | ""            | Username cannot be empty                     |
      | email    | user@gmail.com | user@gmail    | Invalid email format                         |
      | email    | user@gmail.com | ""            | Email cannot be empty                        |
      | password | Password123!   | passwor       | Password must contain at least 8 charcaters  |
      | password | Password123!   | password      | Password must contain an uppercase character |
      | password | Password123!   | PASSWORD      | Password must contain a lowercase character  |
      | password | Password123!   | Password      | Password must contain a number               |
      | password | Password123!   | password123   | Password must contain a special character    |

  Scenario Outline: Give invalid input to a field (Error Flow)
    Given the following users exist in the application:
      | Username | Email           | Bio                   | Password     |
      | user1    | user1@gmail.com | Bio for user1         | Password456! |
      | user2    | user2@gmail.com | Bio for user1         | Password456! |
    When requesting the modification of field <Field> to value <InvalidInput>
    Then message <Message> is issued
    And field <Field> has value <OriginalValue>
    Examples:
      | Field    | OriginalValue  | InvalidInput    | Message                                   |
      | username | user           | user1           | Username "user1" is already taken         |
      | email    | user@gmail.com | user2@gmail.com | An account with this email already exists |