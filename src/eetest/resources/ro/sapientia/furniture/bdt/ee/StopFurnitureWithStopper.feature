Feature: Check if furniture can be stopped
	As furniture tool user I want to be able to stop a furniture

   Scenario: Stop a furniture
   Given that we have the following furniture bodies:
   | id | width  | heigth | depth |
   | 1  | 10     | 10     | 10    |
   And Given that we have the following furniture stoppers:
   | id | startTimeMs | stopTimeMs | active |
   | 1  | 0           | 1000       | false  |
   
   When I stop the furniture body with id "1" with furniture stopper id "1"
   Then the furniture stopper with id "1" should have stopped the furniture with id "1"
   And the database should show furniture stopper with id "1" as active