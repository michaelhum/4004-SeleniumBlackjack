Feature: AI
  To determine the option that the AI of the game will use when it is their turn.

  Scenario: AI should split given two initial cards of the same rank
    Given a card in the AI's hand with the rank 'QUEEN' and suit 'DIAMONDS' and visibility 'true'
    And another card in the AI's hand with the rank 'QUEEN' and suit 'HEARTS' and visibility 'false'
    When it is the AI's turn to make a move
    Then the AI should split their hand
    And the AI's last move should be 'SPLIT'
    And the AI's hand size should remained unchanged