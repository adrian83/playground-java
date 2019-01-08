package com.github.adrian83.akka.supervisor;

import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FaultyActor extends CalculatorActor {

	private LoggingAdapter logger = Logging.getLogger(this);

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchAny(this::handle).build();
	}

	public void handle(Object msg) {
		logger.warning("Exception during calculation for input: {}", msg);
		throw new CalculationException();
	}
	
}
