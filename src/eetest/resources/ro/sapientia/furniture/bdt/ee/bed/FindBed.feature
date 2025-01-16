Feature: Check if the bed endpoint works
  As a furniture tool user
  I want to be able to see all the beds

  Scenario: Single bed
    Given that we have the following bed bodies:
      | height | width | length | wood      | type      |
      | 50     | 70    | 200    | OAK       | TWIN      |
    When I invoke the bed all endpoint
    Then I should get the wood "OAK" for the bed in last position

  Scenario: Multiple beds
    Given that we have the following bed bodies:
      | height | width | length | wood      | type      |
      | 60     | 90    | 210    | WALNUT    | TWIN      |
      | 50     | 80    | 200    | MAPLE     | FULL      |
    When I invoke the bed all endpoint
    Then I should get the wood "MAPLE" for the bed in last position
