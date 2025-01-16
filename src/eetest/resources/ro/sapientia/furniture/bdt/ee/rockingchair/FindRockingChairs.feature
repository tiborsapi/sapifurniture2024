Feature: Check if the find all rocking chairs endpoint works

  As a furniture tool user I want to be able to see all the rocking chairs

  Scenario: One rocking chair
  Given that we have the following rocking chairs:
    | material | with |  height  | rocker_radius | seat_height  |  depth  |
    | wood     | 200  |  250     |  90           |  50          |  30     |

  When I invoke the rocking chairs all endpoint
  Then I should get the material "wood" for the position "0"

  Scenario: Two rocking chairs
  Given that we have the following rocking chairs:
    | material | with |  height  | rocker_radius | seat_height  |  depth  |
    | wood     | 200  |  250     |  90           |  50          |  30     |
    | metal    | 50   |  123     |  43           |  40          |  12     |

  When I invoke the rocking chairs all endpoint
  Then I should get the material "metal" for the position "1"