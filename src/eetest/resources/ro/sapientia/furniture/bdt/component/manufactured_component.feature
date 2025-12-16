Feature: Manage and retrieve manufactured components
  As a manufacturing tool user
  I want to add manufactured components and verify retrieval via the 'all' endpoint

  Scenario: Add one manufactured component and verify
    Given that we have the following manufactured components:
      | name  | type | quantity |
      | Chair | WOOD | 10       |
    When I invoke the manufactured components all endpoint
    Then I should see a manufactured component with type "WOOD"

  Scenario: Add two manufactured components and verify METAL exists
    Given that we have the following manufactured components:
      | name   | type  | quantity |
      | Chair  | WOOD  | 10       |
      | Table  | METAL | 5        |
    When I invoke the manufactured components all endpoint
    Then I should see a manufactured component with type "METAL"
