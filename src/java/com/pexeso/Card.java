package com.pexeso;

import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.*;

public class Card {

  private String cardId;
  private String frontPattern;
  private boolean isFront;

  private Button cardFXML;

  public Card(String cardId, String frontPattern){
    setCardId(cardId);
    setFrontPattern(frontPattern);
    setFront(false);

    // FXML setting
    Button card = new Button();
    card.getStyleClass().add("card-back");
    card.setId(cardId);
    card.setText(frontPattern);
    card.setFont(new Font(0));
    setCardFXML(card);
  }

  public Button getCardFXML() {
    return cardFXML;
  }

  private void setCardFXML(Button cardFXML) {
    this.cardFXML = cardFXML;
  }

  public String getCardId() {
    return cardId;
  }

  private void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getFrontPattern() {
    return frontPattern;
  }

  public void setFrontPattern(String frontPattern) {
    this.frontPattern = frontPattern;
  }

  public boolean isFront() {
    return isFront;
  }

  private void setFront(boolean front) {
    isFront = front;
  }

  public void flip(GridPane grid, Node node) {
    // TODO
    // implement flipping animation
    if(isFront()){
      setFront(false);
      node.setStyle(null);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-back");
      node.setDisable(false);
    }else{
      setFront(true);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-front");
      node.setStyle("-fx-background-image: url(" + getClass().getResource(getFrontPattern())
              .toExternalForm() + ")");
      node.setDisable(true);
    }

    RotateTransition rotator = new RotateTransition(Duration.seconds(0.4), node);
    rotator.setAxis(Rotate.Y_AXIS);
    rotator.setFromAngle(0);
    rotator.setToAngle(360);
    rotator.setInterpolator(Interpolator.LINEAR);
    rotator.play();
  }
}