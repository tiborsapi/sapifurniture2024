Feature: Check if the find all endpoints works
  As a furniture tool user
  I want to be able to see all the furnitureWineRack

  Scenario: One element
    Given that we have the following furniture wineracks:
      | capacity | currentLoad | transparentFront |
      | 100      | 50          | true             |
    When I invoke the winerack all endpoint
    Then I should get the transparentFront "true"

  Scenario: Two element
      Given that we have the following furniture wineracks:
        | capacity | currentLoad | transparentFront |
        | 100      | 50          | true             |
        | 150      | 75          | false            |
      When I invoke the winerack all endpoint
      Then I should get the transparentFront "false"