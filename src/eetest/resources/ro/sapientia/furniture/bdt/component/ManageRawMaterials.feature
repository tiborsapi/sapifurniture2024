Feature: Manage and retrieve raw materials
  As a warehouse manager I want to add raw materials and verify retrieval via the 'all' endpoint

  Scenario: Add one raw material and verify
    Given that we have the following raw materials:
      | type | height | width | length | quantity |
      | WOOD | 10     | 50    | 100    | 5        |
    When I invoke the raw materials all endpoint
    Then I should get the height "10" for the position "0"

  Scenario: Add two raw materials and verify the second item's height
    Given that we have the following raw materials:
      | type  | height | width | length | quantity |
      | WOOD  | 10     | 50    | 100    | 5        |
      | METAL | 20     | 80    | 200    | 3        |
    When I invoke the raw materials all endpoint
    Then I should get the height "20" for the position "1"


