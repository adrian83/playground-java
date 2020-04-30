package com.github.adrian83.akka.supervisor;

import java.util.stream.IntStream;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

  public static void main(String[] args) throws InterruptedException {

    ActorSystem actorSystem = ActorSystem.create("supervisor-test-system");

    Props mainActorProps = Props.create(MainActor.class);
    ActorRef mainActorRef = actorSystem.actorOf(mainActorProps);

    IntStream.range(1, 150).forEach((i) -> mainActorRef.tell(i, ActorRef.noSender()));

    Thread.sleep(20000);

    actorSystem.terminate();
  }
}
