package com.et.akka.stream;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.concurrent.CompletionStage;

@Component
public class MyStream {
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    private ActorMaterializer materializer;
    @PostConstruct
    public void run() {
        Source<Integer, NotUsed> source = Source.range(1, 100);
        Sink<Integer, CompletionStage<Done>> sink = Sink.foreach(System.out::println);
        Flow<Integer, Integer, NotUsed> flow = Flow.of(Integer.class).filter(MyStream::isPrime);

       /* RunnableGraph<NotUsed> graph = source.to(sink);
        graph.run(actorSystem);*/

        source.via(flow).to(sink).run(materializer);

    }
    // Method to check if a number is prime
    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number == 2) {
            return true;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}