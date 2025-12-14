Feature: Component list operations
	As furniture tool user I want to be able to manage component lists

   Scenario: Get all component lists
   Given that we have component lists in the system
   When I invoke the get all component lists endpoint
   Then I should get a list of component lists

   Scenario: Get component list by ID
   Given that we have a component list with id "1"
   When I invoke the get component list by id endpoint with id "1"
   Then I should get a component list with id "1"

   Scenario: Get non-existing component list by ID
   When I invoke the get component list by id endpoint with id "999"
   Then I should get a not found response for component list

