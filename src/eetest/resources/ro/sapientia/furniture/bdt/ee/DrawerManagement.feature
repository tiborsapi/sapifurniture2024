Feature: Drawer Management End-to-End Tests

  Scenario: Create and manage a drawer through the API
    Given I have a drawer with the following specifications
      | material | color  | height | width | depth | weight | maxOpenDistance | weightCapacity | isOpen |
      | Wood     | Brown  | 15.0   | 50.0  | 45.0  | 5.0   | 40.0           | 25.0          | false  |
    When I create the drawer through the API
    Then the API response status should be 201
    And the returned drawer should match the specifications

  Scenario: Retrieve open drawers
    Given there are existing drawers in the system with states
      | material | color  | height | width | depth | weight | maxOpenDistance | weightCapacity | isOpen |
      | Wood     | Brown  | 15.0   | 50.0  | 45.0  | 5.0   | 40.0           | 25.0          | true   |
      | Metal    | Silver | 20.0   | 60.0  | 50.0  | 8.0   | 45.0           | 30.0          | false  |
    When I request all open drawers through the API
    Then the API response status should be 200
    And I should receive only the open drawers