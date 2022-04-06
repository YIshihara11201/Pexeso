package com.pexeso;

public class Meme {
  private String pattern;
  private String text;

  public Meme(String pattern, String text){
    setPattern(pattern);
    setText(text);
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
