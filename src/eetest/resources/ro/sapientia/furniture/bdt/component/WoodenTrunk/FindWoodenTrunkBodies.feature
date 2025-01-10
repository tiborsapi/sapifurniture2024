Feature: Check if the find all endpoints works for wooden trunks
    As a wooden trunk tool user
    I want to be able to see all the wooden trunks

    Scenario: One wooden trunk
        Given that we have the following wooden trunks:
        | height | width | depth | material | hasLid | capacity |
        | 100    | 50    | 40    | oak      | true   | 200      |
        When I invoke the wooden trunk all endpoint
        Then I should get the height "100" for the position "0"

    Scenario: Two wooden trunks
        Given that we have the following wooden trunks:
        | height | width | depth | material | hasLid | capacity |
        | 90    | 50    | 40    | oak      | true   | 200      |
        | 80     | 40    | 30    | pine     | false  | 150      |
        When I invoke the wooden trunk all endpoint
        Then I should get the height "80" for the position "2"