package ab.java.akka;

import akka.actor.UntypedActor;

public class SleepingActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Throwable {
		System.out.println(this.getSelf().path().name() + " - " + msg);
	}

}
