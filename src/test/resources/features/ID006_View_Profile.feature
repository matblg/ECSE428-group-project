Feature: View Profile (Reading History & Collections)
  As a user
  I would like to view my profile information related to reading history and book collections
  So that I can track books I started/completed and browse my reading collections

  Background: users exist and books are resolved from an external API
    Given the following users exist in the System:
      | username | email           | bio               | password     |
      | user     | user@gmail.com  | I love reading!   | Password123! |
      | other    | other@gmail.com | Reading unites us | Password456! |
    And the application is connected to the Books API
    And the user is logged into the account with email "user@gmail.com"

  Scenario Outline: View profile shows Reading History and Collections (Normal Flow)
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
    When the user opens their Profile page
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
    When the user applies Reading History filter "Status" = <FilterStatus>
    Then the Reading History list shows the following <ExpectedCount> titles: <ExpectedTitles>

    Examples:
      | FilterStatus | ExpectedTitles                       | ExpectedCount |
      | STARTED      | The Wandering Isles, The Iron Empire |             2 |
      | COMPLETED    | Algorithms Unlocked                  |             1 |

  Scenario: Reading History shows empty state when user has no history (Alternative flow)
    Given the user has no entries in Reading History
    When the user opens their Profile page
    Then the section "Reading History" shows the message "You haven't started any books yet"

  Scenario: Collections show empty state when user has no saved books (Alternative flow)
    Given the user has no Collections
    When the user opens their Profile page
    Then the section "Collections" shows the message "No saved books yet"
   
  Scenario: Prevent non-owners from viewing Reading History and Collections (Security Check)
    Given the account "other" has Reading History and Collections data
    When user attempts to access the profile of "other"
    Then the sections "Reading History" and "Collections" are not visible
