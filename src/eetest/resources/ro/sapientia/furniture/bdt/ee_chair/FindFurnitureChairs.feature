Feature: Check if the find all endpoints works
	As furniture tool user I want to be able to see all the furnitures

   Scenario: One element
   Given that we have the following furniture chairs:
   |name   |numOfLegs|material |
   |"first"|3 		   |"wood"   |
   When I invoke the furniture all endpoint
   Then I should get the numOfLegs "3" for the position "0"

	 Scenario: Two element in the table
   Given that we have the following furniture chairs:
   | name  	 |numOfLegs|material |
   | "first" |3 		   |"wood" 	 |
	 | "second"|4 		   |"metal"  |
   When I invoke the furniture all endpoint
   Then I should get the numOfLegs "4" for the position "1"