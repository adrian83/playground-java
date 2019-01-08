package com.github.adrian83.akka.supervisor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		ActorSystem actorSystem = ActorSystem.create("supervisor-test-system");
		
		Props mainActorProps = Props.create(MainActor.class);
		ActorRef mainActorRef = actorSystem.actorOf(mainActorProps);
		
		for(int i=1; i< 100; i++){
			mainActorRef.tell(i, ActorRef.noSender());
		}
		
		Thread.sleep(50000);
		
		actorSystem.terminate();
	}

}
