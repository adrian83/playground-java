package com.github.adrian83.akka.supervisor;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.scalatest.junit.JUnitSuite;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

public class MainActorTest extends JUnitSuite {

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
	public void testNoMessagesSentFromMainActor() {

		new TestKit(system) {
			{
				final Props props = Props.create(MainActor.class);
				final ActorRef subject = system.actorOf(props);

				within(Duration.ofSeconds(1), () -> {
					subject.tell(4, getRef());

					expectNoMessage();

					return null;
				});
			}
		};
	}

}
