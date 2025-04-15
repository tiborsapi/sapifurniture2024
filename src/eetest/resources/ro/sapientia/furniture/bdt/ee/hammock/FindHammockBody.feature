Feature: Hammock retrieval

  Scenario: Retrieve hammocks and check lengths
    Given that we have the following hammocks
      | length | width | material | weight |
      | 200    | 100   | Cotton   | 15     |
      | 250    | 120   | Nylon    | 20     |
    When I invoke the hammock all endpoint
    Then I should get the length "200" for the position "0"
    Then I should get the length "250" for the position "1"
