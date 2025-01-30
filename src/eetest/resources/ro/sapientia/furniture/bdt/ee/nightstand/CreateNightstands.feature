Feature: Check if the creation endpoint works
  As a furniture tool user I want to be able to create new nightstand.

  Scenario: Nightstand created successfully
    Given that we have the following nightstand creation data:
      | width | height | depth | numberOfDrawers | hasLamp | color |
      |  10   |   10   |  10   |       10        |   true  | BLACK |
    When I create the nightstand via the API
    Then I should get the color "BLACK" as response.

  Scenario: Nightstand creation fails
    Given that we have the following nightstand creation data:
      | width | height | depth | numberOfDrawers | hasLamp | color |
      |  20   |   20   |  20   |       20        |   true  | WHITE |
    When I create the nightstand via the API
    Then I should get a bad request error response.
