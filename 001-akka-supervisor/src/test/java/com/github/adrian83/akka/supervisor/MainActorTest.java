package com.github.adrian83.akka.supervisor;

import java.math.BigInteger;
import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

public class MainActorTest {

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

        within(
            Duration.ofSeconds(1),
            () -> {
              subject.tell(4, getRef());

              expectNoMessage();

              return null;
            });
      }
    };
  }

  @Test
  public void testShouldHandleCalculationException() {

    new TestKit(system) {
      {
        final Props props = Props.create(CalculatorActor.class);
        final ActorRef subject = system.actorOf(props);

        within(
            Duration.ofSeconds(1),
            () -> {
              int factorial = 1;
              for (int i = 2; i < MainActor.FAULT + 2; i++) {
                factorial = factorial * i;

                subject.tell(i, getRef());
                expectMsg(new CalculationResult(i, BigInteger.valueOf(factorial)));
              }

              return null;
            });
      }
    };
  }
}
