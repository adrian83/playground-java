package ab.java.akka;

import java.util.stream.IntStream;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.Option;


public class MainActor extends UntypedActor {
	
	private static final int CALCULATORS_COUNT = 10;
	
	private int next = 0;
	
	@Override
	public void onReceive(Object msg) throws Throwable {

		if (msg instanceof Integer) {

				 ActorRef calc = this.context().children().toList().apply(next);
				 calc.tell((Integer)msg, ActorRef.noSender());
				 
				 next = (next+1) % CALCULATORS_COUNT;
				 
		} else {
			System.out.println(this.getSelf().path().name() + " - unknown message: " + msg);
		}
	}

	@Override
	public void preStart() throws Exception {
		super.preStart();
		//System.out.println("PRE START: actor " + this.getSelf().path().name());
		
		IntStream.range(0, CALCULATORS_COUNT).forEach(i -> this.context().actorOf(Props.create(CalculatorActor.class)));
	}
	
	@Override
	public void postStop() throws Exception {
		super.postStop();
		//System.out.println("POST STOP: actor " + this.getSelf().path().name());
	}
	
	@Override
	public void preRestart(Throwable reason, Option<Object> msg) throws Exception {
		super.preRestart(reason, msg);
		//System.out.println("PRE RESTART: actor " + this.getSelf().path().name() 
		//		+ ", reason: " + reason + ", msg: " + msg);
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		super.postRestart(reason);
		//System.out.println("POST RESTART: actor " + this.getSelf().path().name() 
		//		+ ", reason: " + reason);
	}
	
}
