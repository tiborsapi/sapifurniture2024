Feature: Delete furniture
	As furniture tool user I want to be able to delete furniture

   Scenario: Delete existing furniture
   Given that we have the following furniture bodies:
   | width  | heigth | depth |
   | 20 		| 30 		 | 15 	 |
   | 25 		| 35 		 | 20 	 |
   When I delete furniture with id "1"
   Then I should get an OK response
   When I invoke the furniture find endpoint with id "1"
   Then I should get a not found response
   When I invoke the furniture all endpoint
   Then I should get "1" furniture bodies in the response

