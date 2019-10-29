package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.Objects;

public class Scheduler {

    private ArrayDeque<Continuation> deque;
    public static final ContinuationScope SCOPE = new ContinuationScope("scope");

    public Scheduler(){
        deque = new ArrayDeque<>();
    }

    public void enqueue(){
        var tmp = Objects.requireNonNull(Continuation.getCurrentContinuation(SCOPE));
        deque.offer(tmp);
        Continuation.yield(SCOPE);
    }

    public void runLoop(){
        while (!deque.isEmpty()){
            var ctn = deque.poll();
            ctn.run();
            if (!ctn.isDone())
                deque.offer(ctn);
        }
    }

}
