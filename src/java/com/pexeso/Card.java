package com.pexeso;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

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

  public void flip(Node node) {
    // TODO
    // implement flipping animation
    if(isFront()){
      setFront(false);
      node.setStyle(null);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-back");
      node.getStyleClass().add("button");
      node.setDisable(false);
    }else{
      setFront(true);
      node.getStyleClass().clear();
      node.getStyleClass().add("card-front");
      node.setStyle("-fx-background-image: url(" + getClass().getResource(getFrontPattern())
          .toExternalForm() + ")");
      node.setDisable(true);
    }
  }
}
