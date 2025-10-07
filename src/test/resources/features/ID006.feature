Feature: View Profile (Reading History & Collections)
  As a User
  I would like to view my profile information related to reading history and book collections
  So that I can track books I started/completed and browse my saved-to-read lists by genre

  Background: Users exist and books are resolved from an external API
    Given the following users exist in the System:
      | Username | Email           | Bio               | Password     |
      | user     | user@gmail.com  | I love reading!   | Password123! |
      | other    | other@gmail.com | Reading unites us | Password456! |
    And the application is connected to the Books API
    And the Books API can resolve the following titles to metadata:
      | Title                          | Author        | Genre       |
      | The Wandering Isles            | C. Farrow     | Fantasy     |
      | Algorithms Unlocked            | T. Cormen     | Non-Fiction |
      | The Iron Empire                | J. Mandell    | Sci-Fi      |
      | River of Stars                 | K. Ishida     | Historical  |
      | Practical Domain-Driven Design | E. Evansworth | Non-Fiction |
      | Sand & Smoke                   | A. Rahim      | Fantasy     |
    And the User with username "user" is logged into the Letterbook System

  Scenario: View profile shows Reading History and Collections (Normal Flow)
    Given the following reading history exists for "user" with the most recent activity first:
      | Title               | Status    |
      | The Iron Empire     | STARTED   |
      | The Wandering Isles | STARTED   |
      | Algorithms Unlocked | COMPLETED |
    And the following collections exist for "user":
      | CollectionName | Genre       | Title                          |
      | To Read        | Fantasy     | Sand & Smoke                   |
      | To Read        | Non-Fiction | Practical Domain-Driven Design |
      | Autumn List    | Historical  | River of Stars                 |
    When the User opens their Profile page
    Then the section "Reading History" is visible
    And it lists the following rows:
      | Title               | Status    |
      | The Iron Empire     | STARTED   |
      | The Wandering Isles | STARTED   |
      | Algorithms Unlocked | COMPLETED |
    And the section "Collections" is visible
    And it displays collections grouped by Genre with the following entries:
      | CollectionName | Genre       | Title                          |
      | To Read        | Fantasy     | Sand & Smoke                   |
      | To Read        | Non-Fiction | Practical Domain-Driven Design |
      | Autumn List    | Historical  | River of Stars                 |

  Scenario Outline: Filter Reading History by status
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

  Scenario Outline: Filter Collections by genre
    Given the following collections exist for "user":
      | CollectionName | Genre       | Title                          |
      | To Read        | Fantasy     | Sand & Smoke                   |
      | To Read        | Non-Fiction | Practical Domain-Driven Design |
      | Autumn List    | Historical  | River of Stars                 |
    When the User applies Collections filter "Genre" = <Genre>
    Then the Collections list shows the following entries:
      | CollectionName   | Genre   | Title   |
      | <CollectionName> | <Genre> | <Title> |
    And the count displayed is <ExpectedCount>

    Examples:
      | Genre       | CollectionName | Title                          | ExpectedCount |
      | Fantasy     | To Read        | Sand & Smoke                   |             1 |
      | Non-Fiction | To Read        | Practical Domain-Driven Design |             1 |
      | Historical  | Autumn List    | River of Stars                 |             1 |

  Scenario: Reading History shows empty state when user has no history
    Given the User has no entries in Reading History
    When the User opens their Profile page
    Then the section "Reading History" shows the message "You haven’t started any books yet"
    And it suggests: "Browse Books" with a link to the catalog

  Scenario: Collections show empty state when user has no saved books
    Given the User has no Collections
    When the User opens their Profile page
    Then the section "Collections" shows the message "No saved books yet"
    And it suggests: "Save books to Collections to read later"

  Scenario: Open a book from Reading History
    Given the following reading history exists for "user":
      | Title               | Status  |
      | The Wandering Isles | STARTED |
    When the User selects the entry "The Wandering Isles" in Reading History
    Then the Book Details page for "The Wandering Isles" is displayed

  Scenario: Open a book from Collections
    Given the following collections exist for "user":
      | CollectionName | Genre   | Title        |
      | To Read        | Fantasy | Sand & Smoke |
    When the User selects the entry "Sand & Smoke" in Collections
    Then the Book Details page for "Sand & Smoke" is displayed

  Scenario: Prevent non-owners from viewing Reading History and Collections (Security Check)
    Given the User with username "other" is logged into the Letterbook System
    And "user" has Reading History and Collections data
    When "other" attempts to access the profile of "user"
    Then the sections "Reading History" and "Collections" are not visible
