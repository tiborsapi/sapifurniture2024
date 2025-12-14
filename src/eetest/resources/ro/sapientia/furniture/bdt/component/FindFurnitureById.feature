Feature: Find furniture by ID
	As furniture tool user I want to be able to find a specific furniture by its ID

   Scenario: Find existing furniture by ID
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 20 		| 30 		 | 15 	 |
   | 25 		| 35 		 | 20 	 |
   When I invoke the furniture find endpoint with id "1"
   Then I should get the furniture with width "20", heigth "30", and depth "15"

   Scenario: Find non-existing furniture by ID
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 20 		| 30 		 | 15 	 |
   When I invoke the furniture find endpoint with id "999"
   Then I should get a not found response

