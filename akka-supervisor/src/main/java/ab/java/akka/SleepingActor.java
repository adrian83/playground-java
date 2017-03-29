package ab.java.akka;

import akka.actor.UntypedActor;
import scala.Option;

public class SleepingActor extends UntypedActor {

	
	
	@Override
	public void onReceive(Object msg) throws Throwable {
		if (msg instanceof Integer) {
			int ms = (Integer)msg * 1000;
			System.out.println(this.getSelf().path().name() + " - will sleep for " + ms + "ms");
			// do not ever do that
			Thread.sleep(ms);
		} else {
			System.out.println(this.getSelf().path().name() + " - unknown message: " + msg);
		}
		
	}
	
	@Override
	public void preStart() throws Exception {
		super.preStart();
		System.out.println("PRE START: actor " + this.getSelf().path().name());
	}
	
	@Override
	public void postStop() throws Exception {
		super.postStop();
		System.out.println("POST STOP: actor " + this.getSelf().path().name());
	}
	
	@Override
	public void preRestart(Throwable reason, Option<Object> msg) throws Exception {
		super.preRestart(reason, msg);
		System.out.println("PRE RESTART: actor " + this.getSelf().path().name() 
				+ ", reason: " + reason + ", msg: " + msg);
	}
	
	@Override
	public void postRestart(Throwable reason) throws Exception {
		super.postRestart(reason);
		System.out.println("POST RESTART: actor " + this.getSelf().path().name() 
				+ ", reason: " + reason);
	}

}
