Feature: Cupboard management
  Testing the cupboard endpoints

  Scenario: Getting cupboard height for one cupboard
  Given that we have the following cupboard bodies:
  | height | width | numberOfDrawer | numberOfCab |
  | 500    | 500   | 5             | 5           |
  When I invoke the cupboard all endpoint
  Then I should get the height "500" for the position "0"

  Scenario: Getting cupboard height for one cupboard
  Given that we have the following cupboard bodies:
  | height | width | numberOfDrawer | numberOfCab |
  | 500    | 500   | 5             | 5           |
  | 600    | 400   | 3             | 4           |

  When I invoke the cupboard all endpoint
  Then I should get the height "600" for the position "1"