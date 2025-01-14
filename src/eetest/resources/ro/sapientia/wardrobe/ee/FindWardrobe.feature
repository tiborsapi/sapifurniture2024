Feature: Check if the find all endpoints works for Wardrobes
    As a wardrobe tool user I want to be able to see all the wardrobes

  Scenario: One element
    Given that we have the following wardrobes:
      | width | heigth | depth | number_of_doors | has_mirror | number_of_shelves |
      | 100   | 200    | 60    | 3                | true        | 4                  |
    When I invoke the wardrobe all endpoint
    Then I should get the heigth "200" for the position "0"

  Scenario: Two elements in the table
    Given that we have the following wardrobes:
      | width | heigth | depth | number_of_doors | has_mirror | number_of_shelves |
      | 100   | 200    | 60    | 3                | true        | 4                  |
      | 120   | 250    | 70    | 4                | false       | 5                  |
    When I invoke the wardrobe all endpoint
    Then I should get the heigth "250" for the position "2"
