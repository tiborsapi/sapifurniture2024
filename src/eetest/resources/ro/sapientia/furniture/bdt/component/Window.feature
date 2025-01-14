Feature: Manage Windows

  Scenario: Retrieve all windows
    Given the database contains the following windows:
      | height | width | glassType |
      | 120    | 80    | Tempered  |
    When I perform a GET request to "/window/all"
    Then the response status should be 200
    And the response should contain:
      | height | width | glassType |
      | 120    | 80    | Tempered  |
