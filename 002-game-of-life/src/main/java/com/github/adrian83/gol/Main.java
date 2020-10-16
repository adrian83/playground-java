package com.github.adrian83.gol;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {

    if (args.length != 2) {
      System.out.println("Provide universe size and number of generations");
      System.exit(1);
    }

    var size = Integer.valueOf(args[0]);
    var generations = Integer.valueOf(args[1]);

    if (size < 10 && size > 100) {
      System.out.println("Universe size should be at least 10 and no more than 100");
      System.exit(1);
    }

    if (generations < 10 && generations > 120) {
      System.out.println("Number of generations should be at least 10 and no more than 120");
      System.exit(1);
    }

    var universe = new Universe(size);

    for (int i = 0; i < generations; i++) {
      clearScreen();
      universe.print();
      universe = universe.nextGeneration();
      pause();
    }
  }

  private static void clearScreen() {
    System.out.print("\033[H\033[2J");
  }

  private static void pause() throws InterruptedException {
    Thread.sleep(1000);
  }
}
