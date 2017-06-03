package ab.java.akka;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;


public class MainActor extends UntypedActor {
	
	private LoggingAdapter logger = Logging.getLogger(this);
	
	private static final int CALCULATORS_COUNT = 10;
	
	private int next = 0;
	
	private static final SupervisorStrategy strategy =
			  new OneForOneStrategy(100, Duration.apply(2, TimeUnit.SECONDS), DeciderBuilder.
			    match(CalculationException.class, e -> SupervisorStrategy.restart()).
			    matchAny(o -> SupervisorStrategy.escalate()).build());

			@Override
			public SupervisorStrategy supervisorStrategy() {
			  return strategy;
			}
	
	@Override
	public void onReceive(Object msg) throws Throwable {

		if (msg instanceof Integer) {

				 ActorRef calc = this.context().children().toList().apply(next);
				 calc.tell((Integer)msg, this.getSelf());
				 
				 next = (next+1) % CALCULATORS_COUNT;
				 
		} else {
			logger.warning("{} - unknown message: {}", this.getSelf().path().name(), msg);
		}
	}

	@Override
	public void preStart() throws Exception {
		super.preStart();
		IntStream.range(0, CALCULATORS_COUNT-1).forEach(i -> this.context().actorOf(Props.create(CalculatorActor.class)));
		this.context().actorOf(Props.create(FaultyActor.class));
	}

}
