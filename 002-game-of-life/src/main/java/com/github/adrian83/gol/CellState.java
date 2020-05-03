package com.github.adrian83.gol;

public enum Cell {
  ALIVE((char) 0x2593),
  DEAD((char) 0x2591),
  EMPTY(' ');

  private static final int size = 3;

  private char repr;

  private Cell(char c) {
    this.repr = c;
  }

  public String representation() {
    return new String(new char[size]).replace("\0", String.valueOf(repr));
  }
}
