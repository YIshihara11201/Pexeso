package com.pexeso;

import static javafx.application.Platform.exit;

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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class Controller implements Initializable {

  @FXML
  private GridPane gridlayout;

  private static final int CARD_PAIR = 15;
  private static final int ROW = 5;

  Player[] players = {
      new Player("Cybill"),
      new Player("David"),
      new Player("Koki")
  };

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Game game = new Game(players);

    // initialize turn label
    Label turnLabel = new Label();
    turnLabel.setText(players[game.getTurn()].getName()+"'s turn");
    turnLabel.setId("turn");
    gridlayout.getChildren().add(turnLabel);

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
            switchTurnTitle(game);
          } else if(pairCardPattern != null){ // pair made
            // remove the paired cards
            removeCardByPattern(pairCardPattern);
            if(game.getDeck().size()==0){
              displayResult(game);
            }
          }
        }
      });

      // set card FXML inside GridPane (parent grid)
      // shift 1 row for placing player's turn label
      GridPane.setRowIndex(currentCard.getCardFXML(), (int) i/ROW+1);
      GridPane.setColumnIndex(currentCard.getCardFXML(), i%ROW);
      gridlayout.getChildren().add(currentCard.getCardFXML());
    }
  }

  private void switchTurnTitle(Game game) {
    Label label = (Label) gridlayout.lookup("#turn");
    label.setText(game.getPlayers()[game.getTurn()].getName() + "'s turn");
  }

  private void removeCardByPattern(String pattern) {
    ObservableList<Node> children = gridlayout.getChildren();
    int removeCount=0;
    for(int i=0; i<children.size(); i++){
      if(children.get(i) instanceof Button && ((Button) children.get(i)).getText().equals(pattern)) {
        children.remove(children.get(i));

        // add invisible cards to keep space for the removed pair
        Button blankSpace = new Button();
        blankSpace.getStyleClass().add("transparent-card");
        blankSpace.setDisable(true);
        GridPane.setRowIndex(blankSpace, (int) i/ROW+1);
        GridPane.setColumnIndex(blankSpace, i%ROW);
        gridlayout.getChildren().add(i,blankSpace);

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

  private void displayResult(Game game){
    gridlayout.getChildren().remove((Label) gridlayout.lookup("#turn"));
    PopupWindow.display(game.getPlayers());
    exit();
  }
}
