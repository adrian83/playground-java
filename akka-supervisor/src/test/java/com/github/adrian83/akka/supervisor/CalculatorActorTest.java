package com.github.adrian83.akka.supervisor;

import java.math.BigInteger;
import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

public class CalculatorActorTest extends JUnitSuite {

	private static final long serialVersionUID = 5398259879494961351L;

	static ActorSystem system;

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testShouldReturnCorrectFraction() {

		new TestKit(system) {
			{
				final Props props = Props.create(CalculatorActor.class);
				final ActorRef subject = system.actorOf(props);

				within(Duration.ofSeconds(1), () -> {
					subject.tell(4, getRef());

					expectMsg(new CalculationResult(4, BigInteger.valueOf(24)));

					return null;
				});
			}
		};
	}

	@Test
	public void testShouldIgnoreMessagesWithUnsuportedTypes() {

		new TestKit(system) {
			{
				final Props props = Props.create(CalculatorActor.class);
				final ActorRef subject = system.actorOf(props);

				within(Duration.ofSeconds(1), () -> {
					subject.tell("invalid msg", getRef());

					expectNoMessage();

					return null;
				});
			}
		};
	}

}
