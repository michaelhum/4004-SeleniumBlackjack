Feature: Win Conditions
  To outline the winning conditions of the game.

  Scenario: Player immediately wins if they have 7 cards without busting
    Given a card in the player's hand with the rank 'THREE' and suit 'DIAMONDS' and hidden 'true'
    And another card in the player's hand with the rank 'THREE' and suit 'HEARTS' and hidden 'false'
    And another card in the player's hand with the rank 'TWO' and suit 'DIAMONDS' and hidden 'false'
    And another card in the player's hand with the rank 'TWO' and suit 'SPADES' and hidden 'false'
    And another card in the player's hand with the rank 'TWO' and suit 'CLUBS' and hidden 'false'
    And another card in the player's hand with the rank 'TWO' and suit 'HEARTS' and hidden 'false'
    When the player draws his seventh card with the rank 'ACE_LOW' and suit 'HEARTS' and hidden 'false'
    And the player's hand value doesn't exceed 21
    Then the player immediately wins with a seven card charlie
    And the player's status should be set
    And the other player's statuses should be set

  Scenario: When players tie with scores, the player lowest amount of cards is the winner
    Given A player playing the game
    And Another player playing the game
    When player 1 has a card with the rank 'THREE' and suit 'HEARTS' and hidden 'false'
    And player 1 has another card with the rank 'SIX' and suit 'HEARTS' and hidden 'false'
    And player 2 has a card with the rank 'NINE' and suit 'HEARTS' and hidden 'false'
    When the game is resolved
    Then player 1 should have a hand status of 'LOSER'
    And player 2 should have a hand status of 'WINNER'
