Feature: Manage and retrieve furniture bodies As a furniture tool user 
I want to add furniture bodies and verify retrieval via the 'all' endpoint

Scenario: Add one furniture and verify Given that we have the following furniture bodies: 
| width | heigth | depth | 
| 10    | 10     | 10    | 
When I invoke the furniture all endpoint 
Then I should get the heigth "10" for the position "0"

Scenario: Add two furnitures and verify the second item Given that we have the following furniture bodies: 
| width | heigth | depth |
| 10    | 10     | 10    | 
| 10    | 12     | 10    | 
When I invoke the furniture all endpoint 
Then I should get the heigth "12" for the position "2"
