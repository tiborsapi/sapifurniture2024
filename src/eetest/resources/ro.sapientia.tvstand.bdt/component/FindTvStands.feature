Feature: Check if the find all endpoints works
	As tv stand tool user I want to be able to see all the tv stand

   Scenario: One element
   Given that we have the following tv stads:
   | width  | heigth | depth | door | material |
   | 10 		| 10 		 | 10 	 | 2        |wood      |
   When I invoke the tv stand all endpoint
   Then I should get the heigth "10" for the position "0"
   
   Scenario: Two element in the table
   Given that we have the following tv stads:
   | width  | heigth | depth |door | material |
   | 10 		| 10 		 | 10 	 |2        |wood      |
   | 10 		| 12 		 | 10 	 |3        |metal      |
   
   When I invoke the tv stand all endpoint
   Then I should get the heigth "12" for the position "1"