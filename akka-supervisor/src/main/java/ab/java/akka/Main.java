package ab.java.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		
		ActorSystem actorSystem = ActorSystem.create("my-actor-system");
		
		Props mainActorProps = Props.create(MainActor.class);
		ActorRef mainActorRef = actorSystem.actorOf(mainActorProps);
		
		
		for(int i=1; i< 1000; i++){
			mainActorRef.tell(i, ActorRef.noSender());
		}
		
		Thread.sleep(40000);
		
		
		actorSystem.terminate();
		
	}

}
