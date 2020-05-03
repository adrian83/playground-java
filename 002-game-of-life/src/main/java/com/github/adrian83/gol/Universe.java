package com.github.adrian83.gol;

import static com.github.adrian83.gol.Cell.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Universe {

  private static final int HUNDRED_PERCENT = 100;
  private static final int INITIAL_ALIVE_PERCENT = 40;
  private static final int INITIAL_DEAD_PERCENT = 30;

  private static final Random RANDOM = new Random(System.currentTimeMillis());

  private Cell[][] board;

  private Universe(Cell[][] board) {
    this.board = board;
  }

  public Universe(int size) {
    this.board = new Cell[size][size];

    IntStream.range(0, size)
        .forEach((x) -> IntStream.range(0, size).forEach((y) -> board[x][y] = initCell(x, y)));
  }

  private Cell initCell(int x, int y) {
    var r = RANDOM.nextInt(HUNDRED_PERCENT);
    if (r < INITIAL_ALIVE_PERCENT) {
      return ALIVE;
    } else if (r < INITIAL_ALIVE_PERCENT + INITIAL_DEAD_PERCENT) {
      return DEAD;
    } else {
      return EMPTY;
    }
  }

  public Universe nextGeneration() {
    var newBoard = new Cell[board.length][board[0].length];

    IntStream.range(0, board.length)
        .forEach(
            (x) ->
                IntStream.range(0, board[0].length)
                    .forEach((y) -> newBoard[x][y] = determineNewCell(board[x][y], x, y)));

    return new Universe(newBoard);
  }

  private long liveNeighboars(int x, int y) {
    return Stream.of(
            getCell(x - 1, y - 1),
            getCell(x, y - 1),
            getCell(x + 1, y - 1),
            getCell(x - 1, y),
            getCell(x + 1, y),
            getCell(x - 1, y + 1),
            getCell(x, y + 1),
            getCell(x + 1, y + 1))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .filter(c -> c == ALIVE)
        .count();
  }

  private Cell determineNewCell(Cell old, int x, int y) {
    switch (old) {
      case EMPTY:
        return old;
      case ALIVE:
        var liveNeighboars1 = liveNeighboars(x, y);
        return liveNeighboars1 > 1 && liveNeighboars1 < 4 ? ALIVE : DEAD;
      case DEAD:
        var liveNeighboars2 = liveNeighboars(x, y);
        return liveNeighboars2 == 3 ? ALIVE : DEAD;
    }
    return old;
  }

  private Optional<Cell> getCell(int x, int y) {
    return onBoard(x, y) ? Optional.of(board[x][y]) : Optional.empty();
  }

  private boolean onBoard(int x, int y) {
    return x >= 0 && x < board.length && y >= 0 && y < board[0].length;
  }

  public void print() {
    System.out.println("");
    Arrays.stream(board).forEach((l) -> System.out.println("|" + lineToString(l) + "|"));
    System.out.println("");
  }

  private String lineToString(Cell[] line) {
    return Arrays.stream(line).map((cell) -> cell.representation()).collect(Collectors.joining());
  }
}
