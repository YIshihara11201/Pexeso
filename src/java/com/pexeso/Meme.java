package com.pexeso;

public class Meme {
  private String text;
//  private String designId;

  public Meme(String text){
    setText(text);
//    setDesignId(designId);
  }

  public String getText() {
    return text;
  }

  private void setText(String text) {
    this.text = text;
  }

}
