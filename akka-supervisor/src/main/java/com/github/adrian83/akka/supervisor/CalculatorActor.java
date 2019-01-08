package com.github.adrian83.akka.supervisor;

import java.math.BigInteger;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CalculatorActor extends AbstractActor {

	private LoggingAdapter logger = Logging.getLogger(this);

	public void calculate(Integer value) {
		BigInteger result = factorial(BigInteger.valueOf(value));
		logger.debug("{} - factorial {} is {}", this.getSelf().path().name(), value, result);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Integer.class, this::calculate).build();
	}

	private BigInteger factorial(BigInteger number) {
		return BigInteger.ONE.equals(number) ? BigInteger.ONE
				: (number.multiply(factorial(number.subtract(BigInteger.ONE))));
	}

}
