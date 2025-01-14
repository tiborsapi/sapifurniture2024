Feature: Check if the find all endpoints works
	As door tool user I want to be able to see all the soors
	
   Scenario: One element
   Given that we have the following doors:
   | width  | heigth | depth | color  |material | hasGlass | numberOfGlassPanels |   
   | 80 	| 200 	 | 3 	 | brown  | wood    | true     | 3                   |
   When I invoke the door all endpoint
   Then I should get the heigth "200" for the position "0"
   
   Scenario: Two element in the table
   Given that we have the following doors:
   | width  | heigth | depth | color  |material | hasGlass | numberOfGlassPanels |   
   | 80 	| 200 	 | 3 	 | brown  | wood    | true     | 3                   |
   | 90 	| 210 	 | 6 	 | gray   | steel   | false    | 0                   |
   
   When I invoke the door all endpoint
   Then I should get the heigth "210" for the position "1"