package ab.java.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class Main {

	public static void main(String[] args) {
		System.out.println("Started");
		
		ActorSystem actorSystem = ActorSystem.create("my-actor-system");
		
		Props sleepingActorProps = Props.create(SleepingActor.class);
		ActorRef sleepingActorRef = actorSystem.actorOf(sleepingActorProps);
		
		sleepingActorRef.tell(6, ActorRef.noSender());
		
		sleepingActorRef.tell(PoisonPill.getInstance(), ActorRef.noSender());
	}

}
