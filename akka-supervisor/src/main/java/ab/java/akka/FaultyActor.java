package ab.java.akka;

import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FaultyActor extends CalculatorActor {

	private LoggingAdapter logger = Logging.getLogger(this);

	@Override
	public void onReceive(Object msg) throws Throwable {
		logger.warning("Exception during calculation for input: {}", msg);
		if(System.currentTimeMillis() % 10 == 0) { // should fail sometimes
			throw new CalculationException();
		}
		super.onReceive(msg);
	}

}
