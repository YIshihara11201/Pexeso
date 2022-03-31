package com.pexeso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class Game {
  private static final int CARD_PAIR = 15;
  private static final int CARD_NUMBER = 2*CARD_PAIR;
  private static final Card[] cards = {
      new Card("1", "/com/pexeso/pictures/1.jpg"),
      new Card("2", "/com/pexeso/pictures/1.jpg"),
      new Card("3", "/com/pexeso/pictures/2.jpg"),
      new Card("4", "/com/pexeso/pictures/2.jpg"),
      new Card("5", "/com/pexeso/pictures/3.jpg"),
      new Card("6", "/com/pexeso/pictures/3.jpg"),
      new Card("7", "/com/pexeso/pictures/4.jpg"),
      new Card("8", "/com/pexeso/pictures/4.jpg"),
      new Card("9", "/com/pexeso/pictures/5.jpg"),
      new Card("10", "/com/pexeso/pictures/5.jpg"),
      new Card("11", "/com/pexeso/pictures/6.jpg"),
      new Card("12", "/com/pexeso/pictures/6.jpg"),
      new Card("13", "/com/pexeso/pictures/7.jpg"),
      new Card("14", "/com/pexeso/pictures/7.jpg"),
      new Card("15", "/com/pexeso/pictures/8.jpg"),
      new Card("16", "/com/pexeso/pictures/8.jpg"),
      new Card("17", "/com/pexeso/pictures/9.jpg"),
      new Card("18", "/com/pexeso/pictures/9.jpg"),
      new Card("19", "/com/pexeso/pictures/10.jpg"),
      new Card("20", "/com/pexeso/pictures/10.jpg"),
      new Card("21", "/com/pexeso/pictures/11.jpg"),
      new Card("22", "/com/pexeso/pictures/11.jpg"),
      new Card("23", "/com/pexeso/pictures/12.jpg"),
      new Card("24", "/com/pexeso/pictures/12.jpg"),
      new Card("25", "/com/pexeso/pictures/13.jpg"),
      new Card("26", "/com/pexeso/pictures/13.jpg"),
      new Card("27", "/com/pexeso/pictures/14.jpg"),
      new Card("28", "/com/pexeso/pictures/14.jpg"),
      new Card("29", "/com/pexeso/pictures/15.jpg"),
      new Card("30", "/com/pexeso/pictures/15.jpg"),
  };

  private ArrayList<Card> deck;
  private Player[] players;
  private int turn;
  private Card[] drawCards = new Card[2];
  private ArrayList<String> pairPatterns = new ArrayList<>();

  public Game(Player[] players) {
    createDeck(cards);
    setPlayers(players);
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

  private void createDeck(Card[] card) {
    this.deck = new ArrayList<>(List.of(cards));
  }

  public Card findCard(String pattern){
    for(Card card: deck){
      if(card.getCardId().equals(pattern)){
        return card;
      }
    }
    return null;
  }

  public String checkPair(ActionEvent actionEvent){
    Button clickedCard = (Button)actionEvent.getSource();
    String clickedCardPattern = clickedCard.getId();

    if(drawCards[0]==null){  // 1st draw
      drawCards[0] = findCard(clickedCardPattern);
    }else if(drawCards[1]==null){  // 2nd draw
      drawCards[1] = findCard(clickedCardPattern);
      // check if pair was made
      // success
      if(drawCards[0].getFrontPattern().equals(drawCards[1].getFrontPattern())){
        String pairCardPattern = drawCards[0].getFrontPattern();
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