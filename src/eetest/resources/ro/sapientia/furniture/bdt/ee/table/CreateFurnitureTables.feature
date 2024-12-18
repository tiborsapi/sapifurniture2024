Feature: Check if the table creation endpoint works properly

  Scenario: Table created successfully
    Given that I have the following table creation data:
      | width | height | depth | color | type   | expectedSuccess |
      | 10    | 20     | 20    | BLACK | office | true    |
    When I create the table via the API
    Then I should get the color "BLACK" and type "OFFICE" as response.

  Scenario: Table creation fails
    Given that I have the following table creation data:
      | width | height | depth | color | type   | expectedSuccess |
      | 10    | 20     | 20    | BLACK | off1ce | false   |
    When I create the table via the API
    Then I should get a bad request error response
