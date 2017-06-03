package ab.java.akka;

import java.math.BigInteger;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

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
	
}
