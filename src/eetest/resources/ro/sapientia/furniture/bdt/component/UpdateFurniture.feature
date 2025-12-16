Feature: Update existing furniture
	As furniture tool user I want to be able to update existing furniture

   Scenario: Update furniture dimensions
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 20 		| 30 		 | 15 	 |
   When I update furniture with id "1" to have width "25", heigth "35", depth "20", and thickness "2"
   Then I should get an OK response with updated furniture having width "25", heigth "35", depth "20", and thickness "2"
   When I invoke the furniture find endpoint with id "1"
   Then I should get the furniture with width "25", heigth "35", and depth "20"

