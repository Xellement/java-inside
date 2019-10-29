package fr.umlv.java.inside;

public class Example1 {

    public static void main(String[] args) {

        var scope = new ContinuationScope("scope1");
        var continuation = new Continuation( scope, () -> {
            //Continuation.yield(scope);
            System.out.println("hello continuation");
        });
        continuation.run();
        continuation.run();
    }

}

/*
Si yield en dehors d'un scope => IllegalStateException

question 4 : on met la méthode en pause en attendant un nouveau run()
question 5 :
question 6 : La continuation est déjà finie une fois et ne peut en effectuer une autre
Normalement, nous voudrions pouvoir relancer plusieur fois une continuatation

 */
