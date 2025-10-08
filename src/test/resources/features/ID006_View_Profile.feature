Feature: View Profile (Reading History & Collections)
  As a User
  I would like to view my profile information related to reading history and book collections
  So that I can track books I started/completed and browse my reading collections

  Background: Users exist and books are resolved from an external API
    Given the following users exist in the System:
      | Username | Email           | Bio               | Password     |
      | user     | user@gmail.com  | I love reading!   | Password123! |
      | other    | other@gmail.com | Reading unites us | Password456! |
    And the application is connected to the Books API
    And the User with username "user" is logged into the Letterbook System

  Scenario: View profile shows Reading History and Collections (Normal Flow)
    Given the following reading history exists for "user":
      | Title               | Status    |
      | The Iron Empire     | STARTED   |
      | The Wandering Isles | STARTED   |
      | Algorithms Unlocked | COMPLETED |
    And the following collections exist for "user":
      | CollectionName | Title                          |
      | To Read        | Sand & Smoke                   |
      | To Read        | Practical Domain-Driven Design |
      | Autumn List    | River of Stars                 |
    When the User opens their Profile page
    Then the section "Reading History" is visible
    And it lists the following rows:
      | Title               | Status    |
      | The Iron Empire     | STARTED   |
      | The Wandering Isles | STARTED   |
      | Algorithms Unlocked | COMPLETED |
    And the section "Collections" is visible
    And it displays the following collection entries:
      | CollectionName | Title                          |
      | To Read        | Sand & Smoke                   |
      | To Read        | Practical Domain-Driven Design |
      | Autumn List    | River of Stars                 |

  Scenario Outline: Filter Reading History by status (Normal Flow)
    Given the following reading history exists for "user":
      | Title               | Status    |
      | The Wandering Isles | STARTED   |
      | Algorithms Unlocked | COMPLETED |
      | The Iron Empire     | STARTED   |
    When the User applies Reading History filter "Status" = <FilterStatus>
    Then the Reading History list shows the following <ExpectedCount> titles: <ExpectedTitles>

    Examples:
      | FilterStatus | ExpectedTitles                       | ExpectedCount |
      | STARTED      | The Wandering Isles, The Iron Empire |             2 |
      | COMPLETED    | Algorithms Unlocked                  |             1 |

  Scenario: Reading History shows empty state when user has no history (Alternative flow)
    Given the User has no entries in Reading History
    When the User opens their Profile page
    Then the section "Reading History" shows the message "You haven’t started any books yet"

  Scenario: Collections show empty state when user has no saved books (Alternative flow)
    Given the User has no Collections
    When the User opens their Profile page
    Then the section "Collections" shows the message "No saved books yet"
   
  Scenario: Prevent non-owners from viewing Reading History and Collections (Security Check)
    Given the User with username "other" is logged into the Letterbook System
    And "user" has Reading History and Collections data
    When "other" attempts to access the profile of "user"
    Then the sections "Reading History" and "Collections" are not visible
