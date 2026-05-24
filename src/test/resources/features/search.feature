Feature: User Search

  Background:
    Given User is on the search page

  Scenario Outline: Successful login with valid credentials
    When User search for a "<query>"
    Then User redirect to the result page

    Examples:
      | query |
      | ubi  |
      | sara |

  Scenario: Search with other valid query
    When User write a query
    And User click search
    Then User redirect to the result page