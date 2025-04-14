Feature: Check if the find all endpoints works
   As bookshelf tool user I want to be able to see all the bookshelves

   Scenario: Add a bookshelf and verify it exists
      Given that the following bookshelves exist:
         | capacity | category       |
         | 20       | FICTION        |
      When I invoke the bookshelf all endpoint
      Then I should get the capacity "20" and category "FICTION" for the position "0"

   Scenario: Add two bookshelves and verify the second
      Given that the following bookshelves exist:
         | capacity | category       |
         | 15       | NON_FICTION    |
         | 25       | HISTORY        |
      When I invoke the bookshelf all endpoint
      Then I should get the capacity "25" and category "HISTORY" for the position "1"
