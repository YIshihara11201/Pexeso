package com.pexeso;

import java.util.ArrayList;

public class Player {
  private String name;
  private ArrayList<Card> points;

  public Player(String name){
    setName(name);
    points = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  private void setName(String name) {
    this.name = name;
  }

  public ArrayList<Card> getPoints() {
    return points;
  }

  @Override
  public String toString() {
    return "Player{ name = " + name +"}";
  }
}
