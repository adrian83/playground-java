package com.github.adrian83.akka.supervisor;

import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import static akka.actor.Props.create;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;

import scala.concurrent.duration.Duration;

import static java.util.stream.IntStream.range;

public class MainActor extends AbstractActor {

	private LoggingAdapter logger = Logging.getLogger(this);

	protected static final int CALCULATORS_COUNT = 10;

	private int next = 0;

	private static final SupervisorStrategy STRATEGY = new OneForOneStrategy(100, Duration.apply(2, TimeUnit.SECONDS),
			DeciderBuilder.match(CalculationException.class, e -> SupervisorStrategy.restart())
					.matchAny(o -> SupervisorStrategy.escalate()).build());

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return STRATEGY;
	}

	@Override
	public void preStart() throws Exception {
		logger.info("Starting MainActor");
		super.preStart();

		range(0, CALCULATORS_COUNT).forEach(i -> this.context().actorOf(create(actor(i + 1))));
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().match(Integer.class, this::send).match(CalculationResult.class, this::print).build();
	}

	private Class<? extends AbstractActor> actor(Integer i) {
		return i % 8 == 0 ? FaultyActor.class : CalculatorActor.class;
	}

	private void print(CalculationResult result) {
		logger.info("{} - factorial {} is {}", this.getSelf().path().name(), result.getNumber(), result.getFactorial());
	}

	private void send(Integer value) {
		ActorRef calc = this.context().children().toList().apply(next);
		calc.tell(value, this.getSelf());

		next = (next + 1) % CALCULATORS_COUNT;
	}

}
