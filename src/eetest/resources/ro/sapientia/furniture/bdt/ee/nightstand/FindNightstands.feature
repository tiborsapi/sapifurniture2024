Feature: Check if the find all endpoint works
  As a furniture tool user I want to be able to see all the nightstands.

  Scenario: One new element in the database
    Given that we have the following nightstands:
      | width | height | depth | numberOfDrawers | hasLamp | color |
      |   1   |    1   |   1   |        1        |   true  | BLACK |
    When I invoke the nightstand all endpoint
    Then I should get the color "BLACK" for the last nightstand in the result.

  Scenario: Two new elements in the database
    Given that we have the following nightstands:
      | width | height | depth | numberOfDrawers | hasLamp | color |
      |   2   |    2   |   2   |        2        |   true  | BLACK |
      |   3   |    3   |   3   |        3        |  false  | WHITE |
      |   4   |    4   |   4   |        4        |   true  | BEIGE |
    When I invoke the nightstand all endpoint
    Then I should get the color "WHITE" for the last nightstand in the result.
