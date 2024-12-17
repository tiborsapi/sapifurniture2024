Feature: Check if the find all endpoints works
  As a furniture tool user I want to be able to see all the tables

  Scenario: One new element in the database
    Given that we have the following furniture tables:
      | width | height | depth | color   | type    |
      | 10    | 10     | 10    | BLACK   | OFFICE  |
    When I invoke the table all endpoint
    Then I should get the color "BLACK" for the last furniture table in the result.

  Scenario: Two new elements in the database
    Given that we have the following furniture tables:
      | width | height | depth | color   | type    |
      | 10    | 10     | 10    | BLACK   | OFFICE  |
      | 12    | 15     | 10    | WHITE   | DINING |
    When I invoke the table all endpoint
    Then I should get the color "WHITE" for the last furniture table in the result.
