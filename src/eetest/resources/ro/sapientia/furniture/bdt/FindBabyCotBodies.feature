Feature: Check if the find all endpoints works
	As baby cot tool user I want to be able to see all the baby cots

   Scenario: One element
   Given that we have the following baby cot bodies:
   | width  | heigth | depth | maxAge | rating |
   | 10 		| 10 		 | 10 	 | 7			| 8			 |
   When I invoke the baby cot all endpoint
   Then I should get the heigth "10" for the position "0"
   
   Scenario: Two element in the table
   Given that we have the following baby cot bodies:
   | width  | heigth | depth | maxAge | rating |
   | 10 		| 10 		 | 10 	 | 5			| 9			 |
   | 10 		| 9 		 | 10 	 | 7			| 6			 |
   
   When I invoke the baby cot all endpoint
   Then I should get the heigth "9" for the position "1"