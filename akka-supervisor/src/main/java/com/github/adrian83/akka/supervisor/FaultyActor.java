package com.github.adrian83.akka.supervisor;

import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FaultyActor extends CalculatorActor {

	private LoggingAdapter logger = Logging.getLogger(this);

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Integer.class, this::handle).build();
	}

	@Override
	public void preStart() throws Exception {
		logger.info("Starting FaultyActor");
		super.preStart();
	}

	public void handle(Integer msg) {
		logger.warning("Exception during calculation for input: {}", msg);
		throw new CalculationException(msg);
	}

}
