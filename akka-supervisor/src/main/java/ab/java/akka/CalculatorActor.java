package ab.java.akka;

import java.math.BigInteger;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class CalculatorActor extends UntypedActor {

	private LoggingAdapter logger = Logging.getLogger(this);
	
	
	@Override
	public void onReceive(Object msg) throws Throwable {
		if (msg instanceof Integer) {
			int number = (Integer) msg;
			BigInteger result = factorial(new BigInteger(msg.toString()));
			logger.debug("{} - factorial {} is {}", this.getSelf().path().name(), number, result);
		} else {
			logger.warning("{} - unknown message: {}", this.getSelf().path().name(), msg);
		}
		
	}
	
	private BigInteger factorial(BigInteger number) {
		return BigInteger.ONE.equals(number) ? BigInteger.ONE 
						: (number.multiply(factorial(number.subtract(BigInteger.ONE))));
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		//System.out.println("PRE START: actor " + this.getSelf().path().name());
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
