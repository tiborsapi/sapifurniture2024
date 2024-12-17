Feature: Check if the find all endpoints works
	As furniture tool user I want to be able to see all the furnitures

   Scenario: One element
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 10 		| 10 		 | 10 	 |
   When I invoke the furniture all endpoint
   Then I should get the heigth "10" for the position "0"
   
   Scenario: Two element in the table
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 10 		| 10 		 | 10 	 |
   | 10 		| 12 		 | 10 	 |
   
   When I invoke the furniture all endpoint
   Then I should get the heigth "12" for the position "1"