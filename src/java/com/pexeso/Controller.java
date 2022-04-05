package com.pexeso;

import static javafx.application.Platform.exit;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
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

  private static final double COL = 5;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Game game = new Game();

    // initialize turn label
    Label turnLabel = new Label();
    turnLabel.setText(game.getPlayers()[game.getTurn()].getName()+"'s turn");
    turnLabel.setId("turn");
    GridPane.setRowIndex(turnLabel, 0);
    gridlayout.getChildren().add(turnLabel);

    // set cards on the field
    ArrayList<Card> deck = game.getDeck();
    Collections.shuffle(deck);
    for(int i=0; i<deck.size(); i++) {
      Card currentCard = deck.get(i);

      // event setting for a card
      currentCard.getCardFXML().setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent actionEvent) {
          currentCard.flip((Node)actionEvent.getSource());
          // check if pair was made
          String pairCardPattern = game.checkPair(actionEvent);
          ArrayList<String> pairedPatterns = game.getPairPatterns();
          if(pairCardPattern.equals("fail")){ // fail making pair

            // delay timing for turning back failed pair
            // https://java.tutorialink.com/how-do-i-put-something-on-an-fx-thread
            // https://stackoverflow.com/questions/21083945/how-to-avoid-not-on-fx-application-thread-currentthread-javafx-application-th
            // https://www.demo2s.com/java/javafx-platform-runlater-runnable-runnable.html
            // https://www.bold.ne.jp/engineer-club/java-thread
            gridlayout.getChildren().forEach(node->{
              if(!(node instanceof Label)){
                node.setDisable(true);
              }
            });

            TimerTask turnBack = new TimerTask() {
              public void run() {
                Platform.runLater(()-> {
                  turnBackAllCards(game);
                });
              }
            };
            Timer timer = new Timer();
            timer.schedule(turnBack, 2000);

            if(game.getPairPatterns().size()!=0){ // when failed after second try
              TimerTask remove = new TimerTask() {
                public void run() {
                  Platform.runLater(()-> {
                    removeCardByPatterns(pairedPatterns);
                  });
                }
              };
              timer.schedule(remove, 3000);
            }

            TimerTask enableCells = new TimerTask() {
              public void run() {
                Platform.runLater(()-> {
                  gridlayout.getChildren().forEach(node->{
                    node.setDisable(false);
                  });
                });
              }
            };
            timer.schedule(enableCells, 3500);
            switchTurnTitle(game);
          } else if(game.getDeck().size()==0){ // game end
            Timer timer = new Timer();
            TimerTask removeCards = new TimerTask() {
              public void run() {
                Platform.runLater(()-> {
                  removeCardByPatterns(pairedPatterns);
                });
              }
            };
            TimerTask popupResult = new TimerTask() {
              public void run() {
                Platform.runLater(()-> {
                  displayResult(game);
                });
              }
            };

            timer.schedule(removeCards, 2000);
            timer.schedule(popupResult, 2500);
          }
        }
      });

      // set card FXML inside GridPane (parent grid)
      // shift 1 row for placing player's turn label
      GridPane.setRowIndex(currentCard.getCardFXML(), (int) (1+i/COL));
      GridPane.setColumnIndex(currentCard.getCardFXML(), (int)(i%COL));
      gridlayout.getChildren().add(currentCard.getCardFXML());
    }
  }

  private void switchTurnTitle(Game game) {
    Label label = (Label) gridlayout.lookup("#turn");
    label.setText(game.getPlayers()[game.getTurn()].getName() + "'s turn");
  }

  private void removeCardByPatterns(ArrayList<String> patterns) {
    ObservableList<Node> children = gridlayout.getChildren();
    for (String pattern : patterns) {
      int removeCount = 0;
      for (int j = 1; j < children.size(); j++) {
        if (children.get(j) instanceof Button
                && ((Button) children.get(j)).getText().equals(pattern)) {
          children.remove(children.get(j));

          // add invisible cards to keep space for the removed pair
          Button blankSpace = new Button();
          blankSpace.getStyleClass().add("transparent-card");
          blankSpace.setId("blank");
          blankSpace.setDisable(true);
          GridPane.setRowIndex(blankSpace, (int)Math.ceil(j/COL));
          GridPane.setColumnIndex(blankSpace, (int)((j-1)%COL));
          gridlayout.getChildren().add(j, blankSpace);
          removeCount++;
          if (removeCount == 2)
            break;
          j--;
        }
      }
    }
    patterns.clear();
  }

  private void turnBackAllCards(Game game) {
    ObservableList<Node> cardNodes = gridlayout.getChildren();
    ArrayList<Card> deck = game.getDeck();
    for(int i=0; i<deck.size(); i++) { // O(n^2) must be modified
      Card currentCard = deck.get(i);
      if(currentCard.isFront()){
        for(Node node: cardNodes){
          if(!node.getId().equals("blank") && node.getId().equals(currentCard.getCardId())){
            currentCard.flip(node);
          }
        }
      }
    }
  }

  private void displayResult(Game game){
    gridlayout.getChildren().remove((Label) gridlayout.lookup("#turn"));
    PopupWindow.display(game.getPlayers());
    System.exit(0);
  }
}