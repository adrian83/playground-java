package com.github.adrian83.akka.supervisor;

import java.math.BigInteger;

import akka.actor.AbstractActor;

public class CalculatorActor extends AbstractActor {

	public void calculate(Integer value) {
		BigInteger factorialRes = factorial(BigInteger.valueOf(value));
		CalculationResult result = new CalculationResult(value, factorialRes);
		getSender().tell(result, getSelf());
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
