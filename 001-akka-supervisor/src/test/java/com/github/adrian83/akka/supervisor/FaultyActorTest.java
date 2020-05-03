package com.github.adrian83.akka.supervisor;

import java.time.Duration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;

public class FaultyActorTest {

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
  public void testNoMessagesSentFromFaultyActor() {

    new TestKit(system) {
      {
        final Props props = Props.create(FaultyActor.class);
        final ActorRef subject = system.actorOf(props);

        within(
            Duration.ofSeconds(1),
            () -> {
              subject.tell("whatever", getRef());

              expectNoMessage();

              return null;
            });
      }
    };
  }
}
