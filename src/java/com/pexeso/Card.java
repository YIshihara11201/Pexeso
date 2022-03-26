package com.pexeso;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class Card {
  private String cardId;
  private String pattern;
  private boolean isFront;

  private Button cardFXML;

  public Card(String cardId, String pattern){
    setCardId(cardId);
    setPattern(pattern);
    setFront(false);

    // FXML setting
    Button card = new Button();
    card.getStyleClass().add("card-back");
    card.setId(cardId);
    card.setText(pattern);
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

  public String getPattern() {
    return pattern;
  }

  private void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public boolean isFront() {
    return isFront;
  }

  private void setFront(boolean front) {
    isFront = front;
  }

  public void flip(Node node) {
    // TODO
    // implement flipping animation
    if(isFront()){
      setFront(false);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-back");
      node.setDisable(false);
    }else{
      setFront(true);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-front");
      node.setDisable(true);
    }
  }
}
