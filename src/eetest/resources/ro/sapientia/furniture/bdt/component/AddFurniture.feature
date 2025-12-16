Feature: Add new furniture
	As furniture tool user I want to be able to add new furniture

   Scenario: Add a new furniture body
   When I add a new furniture body with width "30", heigth "40", depth "25", and thickness "2"
   Then I should get a created response with furniture having width "30", heigth "40", depth "25", and thickness "2"
   And the furniture should have an ID assigned

   Scenario: Add furniture and verify it exists
   When I add a new furniture body with width "50", heigth "60", depth "30", and thickness "3"
   Then I should get a created response
   When I invoke the furniture all endpoint
   Then I should find a furniture with width "50" in the list

