Feature: Verify the furniture "find all" endpoint
  As a furniture tool user
  I want to retrieve all hammock bodies from the database
  So that I can see the details of all available hammocks

  Background:
    Given the furniture database is empty

  Scenario: One hammock body in the database
    Given that the following hammock bodies exist in the database:
      | length | width | material     | weight |
      | 200    | 100   | cotton       | 3.5    |
    When I invoke the "GET /hammock/all" endpoint
    Then I should receive a response with status code 200
    And I should get a hammock body with the following details at position "0":
      | length | width | material | weight |
      | 200    | 100   | cotton   | 3.5    |

  Scenario: Two hammock bodies in the database
    Given that the following hammock bodies exist in the database:
      | length | width | material     | weight |
      | 200    | 100   | cotton       | 3.5    |
      | 180    | 90    | polyester    | 2.8    |
    When I invoke the "GET /hammock/all" endpoint
    Then I should receive a response with status code 200
    And I should get a hammock body with the following details at position "0":
      | length | width | material | weight |
      | 200    | 100   | cotton   | 3.5    |
    And I should get a hammock body with the following details at position "1":
      | length | width | material  | weight |
      | 180    | 90    | polyester | 2.8    |
