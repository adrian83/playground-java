package com.github.adrian83.gol;

public enum CellState {
  ALIVE((char) 0x2593),
  DEAD((char) 0x2591),
  EMPTY(' ');

  private static final int REPR_SIZE = 3;

  private char repr;

  private CellState(char c) {
    this.repr = c;
  }

  public String representation() {
    return new String(new char[REPR_SIZE]).replace("\0", String.valueOf(repr));
  }
}
