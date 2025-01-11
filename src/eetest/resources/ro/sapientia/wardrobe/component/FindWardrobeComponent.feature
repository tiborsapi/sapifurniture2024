Feature: Component Test for Wardrobe Endpoints
    As a developer, I want to ensure the wardrobe endpoints function correctly

  Scenario: Retrieve all wardrobes
    Given the database is initialized with wardrobes
    When a GET request is made to "/wardrobe/all"
    Then the response status should be 200
    And the response should contain wardrobes
