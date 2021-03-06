package com.pexeso;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class Game {
  private static final int CARD_PAIR = 15;

  private ArrayList<Card> deck = new ArrayList<>();
  private Player[] players;
  private int turn;
  private Card[] drawCards = new Card[2];
  private ArrayList<String> pairPatterns = new ArrayList<>();

  public Game() {
    createDeck();
    setPlayers(new Player[]{
        new Player("Player 1"),
        new Player("Player 2"),
    });
    setTurn(0);
  }

  public ArrayList<Card> getDeck(){
    return deck;
  }

  public Player[] getPlayers() {
    return players;
  }

  private void setPlayers(Player[] players) {
    this.players = players;
  }

  public int getTurn() {
    return turn;
  }

  private void setTurn(int turn) {
    this.turn = turn;
  }

  public ArrayList<String> getPairPatterns() {
    return pairPatterns;
  }

  private void createDeck() {
    for (int i=0; i<CARD_PAIR*2; i++){
      String imagePath = String.format("/com/pexeso/pictures/%d.jpg", (i/2)+1);
      this.deck.add(new Card(String.valueOf(i+1), new Meme(imagePath, imagePath)));
    }
  }

  public Card findCard(String id){
    for(Card card: deck){
      if(card.getCardId().equals(id)){
        return card;
      }
    }
    return null;
  }

  public String checkPair(ActionEvent actionEvent){
    Button clickedCard = (Button)actionEvent.getSource();
    String clickedCardId = clickedCard.getId();

    if(drawCards[0]==null){  // 1st draw
      drawCards[0] = findCard(clickedCardId);
    }else if(drawCards[1]==null){  // 2nd draw
      drawCards[1] = findCard(clickedCardId);
      // check if pair was made
      // success
      if(drawCards[0].getMeme().getPattern().equals(drawCards[1].getMeme().getPattern())){
        String pairCardPattern = drawCards[0].getMeme().getPattern();
        // retain pair pattern until fail
        pairPatterns.add(pairCardPattern);

        // add point for player
        players[getTurn()].getPoints().add(drawCards[0]);

        // remove paired card after point added
        deck.remove(drawCards[0]);
        deck.remove(drawCards[1]);

        // reset cards drew
        drawCards[0]=null;
        drawCards[1]=null;

        return pairCardPattern;
      }else {
        // reset cards drew
        drawCards[0]=null;
        drawCards[1]=null;

        // switch to next player turn
        setTurn((getTurn()+1)%players.length);

        return "fail";
      }
    }
    // for 1st draw
    return "first-draw";
  }
}