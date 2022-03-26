package com.pexeso;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class Controller implements Initializable {

  @FXML
  private GridPane gridlayout;

  private static final int CARD_PAIR = 15;
  private static final int ROW = 5;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Player players[] = {
        new Player("Cybill"),
        new Player("David"),
        new Player("Koki")
    };

    Game game = new Game(players);

    // set cards on the field
    ArrayList<Card> deck = game.getDeck();
    for(int i=0; i<deck.size(); i++) {
      Card currentCard = deck.get(i);

      // event setting for a card
      currentCard.getCardFXML().setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent actionEvent) {
          currentCard.flip((Node)actionEvent.getSource());

          // check if pair was made
          String pairCardPattern = game.checkPair(actionEvent);
          if(pairCardPattern == "fail"){ // fail making pair
            turnBackAllCards(game);
          } else if(pairCardPattern != null){ // pair made
            // remove the paired cards
            removeCardByPattern(pairCardPattern);
          }
        }
      });
      // set card FXML inside GridPane (parent grid)
      GridPane.setRowIndex(currentCard.getCardFXML(), (int) i/ROW);
      GridPane.setColumnIndex(currentCard.getCardFXML(), i%ROW);
      gridlayout.getChildren().add(currentCard.getCardFXML());
    }
  }


  private void removeCardByPattern(String pattern) {
    ObservableList<Node> children = gridlayout.getChildren();
    int removeCount=0;
    for(int i=0; i<children.size(); i++){
      if(children.get(i) instanceof Button && ((Button) children.get(i)).getText().equals(pattern)) {
        children.remove(children.get(i));
        removeCount++;
        if(removeCount==2) break;
        i--;
      }
    }
  }

  private void turnBackAllCards(Game game) {
    ObservableList<Node> cardNodes = gridlayout.getChildren();
    ArrayList<Card> deck = game.getDeck();
    for(int i=0; i<deck.size(); i++) { // O(n^2) must be modified
      Card currentCard = deck.get(i);
      if(currentCard.isFront()){
        for(Node node: cardNodes){
          if(node.getId().equals(currentCard.getCardId())){
            currentCard.flip(node);
          }
        }
      }
    }
  }
}
