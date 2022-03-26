package com.pexeso;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class Game {
  private static final int CARD_PAIR = 15;
  private static final int CARD_NUMBER = 2*CARD_PAIR;
  private static final Card[] cards = {
      new Card("1", "text1"),
      new Card("2", "text1"),
      new Card("3", "text2"),
      new Card("4", "text2"),
      new Card("5", "text3"),
      new Card("6", "text3"),
      new Card("7", "text4"),
      new Card("8", "text4"),
      new Card("9", "text5"),
      new Card("10", "text5"),
      new Card("11", "text6"),
      new Card("12", "text6"),
      new Card("13", "text7"),
      new Card("14", "text7"),
      new Card("15", "text8"),
      new Card("16", "text8"),
      new Card("17", "text9"),
      new Card("18", "text9"),
      new Card("19", "text10"),
      new Card("20", "text10"),
      new Card("21", "text11"),
      new Card("22", "text11"),
      new Card("23", "text12"),
      new Card("24", "text12"),
      new Card("25", "text13"),
      new Card("26", "text13"),
      new Card("27", "text14"),
      new Card("28", "text14"),
      new Card("29", "text15"),
      new Card("30", "text15"),

  };

  @FXML
  private GridPane gridlayout;

  private ArrayList<Card> deck;
  private Player[] players;
  private int turn;
  private Card[] drawCards = new Card[2];

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
      if(drawCards[0].getPattern().equals(drawCards[1].getPattern())){
        String pairCardPattern = drawCards[0].getPattern();

        // add point for player
        players[getTurn()].getPoints().add(drawCards[0]);
        // remove paired card after point added
        deck.remove(drawCards[0]);
        deck.remove(drawCards[1]);
        // reset draw cards
        drawCards[0]=null;
        drawCards[1]=null;

        return pairCardPattern;
      }else {
        // reset draw cards
        drawCards[0]=null;
        drawCards[1]=null;
        // switch to next player turn
        setTurn((getTurn()+1)%players.length);

        return "fail";
      }
    }
    // for 1st draw
    return null;
  }

}
