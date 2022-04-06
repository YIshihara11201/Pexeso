package com.pexeso;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.animation.*;

public class Card {

  private String cardId;
  private Meme meme;
  private boolean isFront;
  private Button cardFXML;

  public Card(String cardId, Meme meme){
    setCardId(cardId);
    setMeme(meme);
    setFront(false);

    // FXML setting
    Button card = new Button();
    card.getStyleClass().add("card-back");
    card.setId(cardId);
    card.setText(getMeme().getText());
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

  public Meme getMeme() {
    return meme;
  }

  public void setMeme(Meme meme) {
    this.meme = meme;
  }

  public boolean isFront() {
    return isFront;
  }

  private void setFront(boolean front) {
    isFront = front;
  }

  public void flip(Node cardElem) {
    // TODO
    // implement flipping animation
    if(isFront()){
      setFront(false);
      cardElem.setStyle(null);
      cardElem.getStyleClass().clear();
      cardElem.getStyleClass().add("button");
      cardElem.getStyleClass().add("card-back");
      cardElem.setDisable(false);
    }else{
      setFront(true);
      cardElem.getStyleClass().clear();
      cardElem.getStyleClass().add("card-front");
      cardElem.setStyle("-fx-background-image: url(" + getClass().getResource(getMeme().getPattern())
          .toExternalForm() + ")");
      cardElem.setDisable(true);
    }

    RotateTransition rotator = new RotateTransition(Duration.seconds(0.4), cardElem);
    rotator.setAxis(Rotate.Y_AXIS);
    rotator.setFromAngle(0);
    rotator.setToAngle(360);
    rotator.setInterpolator(Interpolator.LINEAR);
    rotator.play();
  }
}