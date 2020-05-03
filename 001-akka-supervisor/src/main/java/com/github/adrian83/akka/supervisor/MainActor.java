package com.github.adrian83.akka.supervisor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import static akka.actor.Props.create;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class MainActor extends AbstractActor {

  protected static final int FAULT = 4;

  private LoggingAdapter logger = Logging.getLogger(this);

  private int next = 0;

  private SupervisorStrategy supervisorStrategy =
      new OneForOneStrategy(
          -1,
          Duration.Inf(),
          DeciderBuilder.match(CalculationException.class, this::onCalculationException)
              .matchAny(o -> SupervisorStrategy.escalate())
              .build());

  @Override
  public SupervisorStrategy supervisorStrategy() {
    return supervisorStrategy;
  }

  @Override
  public void preStart() throws Exception {
    logger.info("Starting MainActor");
    super.preStart();
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(Integer.class, this::send)
        .match(CalculationResult.class, this::print)
        .build();
  }

  private Class<? extends AbstractActor> actor(Integer i) {
    return i % FAULT == 0 ? FaultyActor.class : CalculatorActor.class;
  }

  private void print(CalculationResult result) {
    logger.info(
        "{} - factorial {} is {}",
        this.getSelf().path().name(),
        result.getNumber(),
        result.getFactorial());
  }

  private void send(Integer value) {
    ActorRef calculator = this.context().actorOf(create(actor(next)));
    calculator.tell(value, this.getSelf());
    next++;
  }

  private Directive onCalculationException(CalculationException ex) {
    send(ex.getValue());
    return SupervisorStrategy.restart();
  }
}
