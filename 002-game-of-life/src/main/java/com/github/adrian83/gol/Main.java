package com.github.adrian83.gol;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws InterruptedException, IOException {
	  
	  if(args.length != 2) {
		  System.out.println("Provide universe size and number of generations");
		  System.exit(1);
	  }
	  
	  var size = Integer.valueOf(args[0]);
	  var generations = Integer.valueOf(args[1]);

	  var universe = new Universe(size);
	  
	  for(int i=0; i<generations; i++) {
		  System.out.print("\033[H\033[2J");
		  universe.print();
		  universe = universe.nextGeneration();
		  Thread.sleep(1000);
	  }

  }
}
