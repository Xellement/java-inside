package fr.umlv.java.inside;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Scheduler {

    enum SchedulerPolicy { STACK, FIFO, RANDOM}

    private Collection<Continuation> deque;
    private final SchedulerPolicy schedulerPolicy;

    static final ContinuationScope SCOPE = new ContinuationScope("scope");

    Scheduler(){
        this(SchedulerPolicy.RANDOM);
    }

    private Scheduler(SchedulerPolicy policy){
        schedulerPolicy = policy;
        if (policy == SchedulerPolicy.RANDOM)
            deque = new ArrayList<>();
        else
            deque = new ArrayDeque<>();
    }

    void enqueue(){
        if (schedulerPolicy == SchedulerPolicy.RANDOM)
            deque.add(Objects.requireNonNull(Continuation.getCurrentContinuation(SCOPE)));
        else
            ((ArrayDeque<Continuation>)deque).offer(Objects.requireNonNull(Continuation.getCurrentContinuation(SCOPE)));
        Continuation.yield(SCOPE);
    }

    void runLoop(){
        Continuation tmp;
        var finished = false;
        while(!deque.isEmpty() && !finished) {
            switch (schedulerPolicy) {
                case STACK:
                    tmp = ((ArrayDeque<Continuation>)deque).pollLast();
                    break;
                case FIFO:
                    tmp = ((ArrayDeque<Continuation>)deque).pollFirst();
                    break;
                default:
                    tmp = ((ArrayList<Continuation>)deque).get(ThreadLocalRandom.current().nextInt(0, deque.size()));
            }
            if (!tmp.isDone())
                tmp.run();
            else
                finished = true;
        }
    }

    public static void main(String[] args) {

        var scope = Scheduler.SCOPE;
        for (SchedulerPolicy value : SchedulerPolicy.values()) {
            System.out.println("Politque d'éxecution : "+value);
            var scheduler = new Scheduler(value);
            var ctn1 = new Continuation(scope, () -> {
                System.out.println("start 1");
                scheduler.enqueue();
                System.out.println("middle 1");
                scheduler.enqueue();
                System.out.println("end 1");
            });
            var ctn2 = new Continuation(scope, () -> {
                System.out.println("start 2");
                scheduler.enqueue();
                System.out.println("middle 2");
                scheduler.enqueue();
                System.out.println("end 2");
            });
            var list = List.of(ctn1, ctn2);
            list.forEach(Continuation::run);
            scheduler.runLoop();
        }
    }
}
