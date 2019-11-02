package fr.umlv.java.inside;

import java.util.ArrayDeque;
import java.util.List;

public class Example1 {

    public static void main(String[] args) {

        var scope = Scheduler.SCOPE;
        var scheduler = new Scheduler();

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
        var ctn3 = new Continuation(scope, () -> {
            System.out.println("start 3");
            scheduler.enqueue();
            System.out.println("middle 3");
            scheduler.enqueue();
            System.out.println("end 3");
        });

        var list = List.of(ctn1, ctn2, ctn3);
        list.forEach(Continuation::run);
        scheduler.runLoop();
    }

}

/*
Si yield en dehors d'un scope => IllegalStateException

question 4 : on met la méthode en pause en attendant un nouveau run()
question 5 :
question 6 : La continuation est déjà finie une fois et ne peut en effectuer une autre
Normalement, nous voudrions pouvoir relancer plusieur fois une continuatation
question 9 : nous avons un Pinned MONITOR = on punaise la continuation dans le thread actuel que
nous pouvons pas déplacer
question 11 : Un thread peut executer plusieur Continuation !!!

 */
