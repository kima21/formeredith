package com.example.ashley.gamestate.uno;

import java.util.ArrayList;
import java.util.Random;

public class State {
    int player1Id;
    int player2Id;
    int deckSize;
    int turn;
    Card deck[] = new Card[108];
    ArrayList<Card> hand1 = new ArrayList<Card>(7);
    ArrayList<Card> hand2 = new ArrayList<Card>(7);
    Card discardPile;
    Random r = new Random();

    //Default State constructor
    public State() {
        player1Id = 0;
        player2Id = 1;
        deckSize = 108;
        turn = 0;
        makeDeck();
        shuffleDeck();
        int i;
        for(i=0; i < 7; i++){
            drawCard(hand1);
            drawCard(hand2);
        }
        discardPile = deck[0];
        for(i = 1; i < 108; i++){
            deck[i-1] = deck[i];
            deck[i] = null;
        }
        updateDeckSize();
    }

    //Method to initialize and make the deck
    public void makeDeck() {
        int i, n, k, j;

        //number cards with value 0
        for (i = 0; i < 4; i++) {
            deck[i] = new Card(i, 0, 'n',"card"+i);
        }

        //number cards with values 1-9
        while (i < 76) {
            for (n = 0; n < 2; n++) {
                for (k = 0; k < 10; k++) {
                    for (j = 0; j < 4; j++) {
                        deck[i] = new Card(j, k, 'n',"card"+i);
                        i++;
                    }
                }
            }
        }

        //skip cards
        while (i < 84) {
            for (k = 0; k < 2; k++) {
                for (j = 0; j < 4; j++) {
                    deck[i] = new Card(j, 0, 's',"card"+i);
                    i++;
                }
            }
        }

        //draw 2 cards
        while (i < 92) {
            for (k = 0; k < 2; k++) {
                for (j = 0; j < 4; j++) {
                    deck[i] = new Card(j, 0, 'd',"card"+i);
                    i++;
                }
            }
        }

        //reverse cards
        while (i < 100) {
            for (k = 0; k < 2; k++) {
                for (j = 0; j < 4; j++) {
                    deck[i] = new Card(j, 0, 'r',"card"+i);
                    i++;
                }
            }
        }

        //wild cards
        while (i < 104) {
            for (j = 0; j < 4; j++) {
                deck[i] = new Card(4, 0, 'w',"card"+i);
                i++;
            }
        }

        //wild draw 4 cards
        while (i < 108) {
            for (j = 0; j < 4; j++) {
                deck[i] = new Card(4, 0, 'd',"card"+i);
                deck[i].setId("card" + i);
                i++;
            }
        }
    }

    //Method to shuffle the deck
    public void shuffleDeck(){
        Card temp[] = new Card[108];
        int i;
        int n;
        int index = r.nextInt(108);
        temp[0] = deck[index];
        for(i = 1;  i < 108; i++){
            index = r.nextInt(108);
            temp[i] = deck[index];
            //detects if the value from the random index is equal to another one already in temp
            for(n = 0; n < 108; n++){
                while(temp[i] == temp[n] && temp[n] != null){
                    index = r.nextInt(108);
                    temp[i] = deck[index];
                }
            }
        }
        //sets the deck in use to the randomly ordered one
        for(i = 0; i < 108; i++){
            deck[i] = temp[i];
        }
    }

    //Method to add a card to a specific hand
    public void drawCard(ArrayList<Card> hand){
        int i;
        hand.add(deck[0]);
        deck[0] = null;
        for(i = 1; i < 108; i++){
            deck[i-1] = deck[i];
            deck[i] = null;
        }
    }

    //Method to update deckSize
    public void updateDeckSize(){
        int i = 0;
        while(deck[i] != null){
            i++;
        }
        deckSize = i;
    }

    //Method to get the deck size
    public int getDeckSize(){
        return deckSize;
    }

    //Method to get a formatted String describing the basic game state
    public String getGameState() {
        return "Player 1 ID: " + player1Id + "Player 2 ID: " + player2Id + ", Deck Size: " + deckSize + ", Turn: " + turn;
    }

    //set/get methods for the player IDs
    public void setPlayer1Id(int pid){
        player1Id = pid;
    }

    public int getPlayer1Id(){
        return player1Id;
    }

    public void setPlayer2Id(int pid){
        player2Id = pid;
    }

    public int getPlayer2Id(){
        return player2Id;
    }

    /**
     * toString
     *
     * @return Game State
     */
    @Override
    public String toString() {
        int i;
        updateDeckSize();
        String out = "Deck Cards: ";
        for (i = 0; i < 108; i++) {
            out = out + "ID: "+deck[i].getId() + " Value: " + deck[i].getValue() + " Type: " + deck[i].getType() + " Color: " + deck[i].getColor() + ", ";
        }
        out = out + "Deck size: " + getDeckSize() + " Turns: " + turn + " " + " Discard Pile: ID:" + discardPile.getId() + " Value: " + discardPile.getValue() + " Type: " + discardPile.getType() + " Color: " + discardPile.getColor();
        for (i = 0; i < hand1.size(); i++) {
            out = out + "hand1 card"+i+": " + "Color: " + hand1.get(i).getColor() + " Value: " + hand1.get(i).getValue() + " Type: " + hand1.get(i).getType() + " " + "ID: " + hand1.get(i).getId();
        }
        for (i = 0; i < hand2.size(); i++) {
            out = out + "hand2 card"+i+": " + "Color: " + hand2.get(i).getColor() + " Value: " + hand2.get(i).getValue() + " Type: " + hand2.get(i).getType() + " " + "ID: " + hand2.get(i).getId();
        }
        out = out + "Player 1 ID: "+player1Id+" Player 2 ID: "+player2Id;
        return out;
    }

}

