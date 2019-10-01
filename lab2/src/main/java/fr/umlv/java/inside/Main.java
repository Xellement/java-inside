package fr.umlv.java.inside;

import java.util.Arrays;

public class Main {
    /*public static String toJSON(Person person) {
        return
                "{\n" +
                        "  \"firstName\": \"" + person.getFirstName() + "\"\n" +
                        "  \"lastName\": \"" + person.getLastName() + "\"\n" +
                        "}\n";
    }

    public static String toJSON(Alien alien) {
        return
                "{\n" +
                        "  \"planet\": \"" + alien.getPlanet() + "\"\n" +
                        "  \"members\": \"" + alien.getAge() + "\"\n" +
                        "}\n";
    }*/

    public static String toJSON(Object obj){
        Arrays.stream(obj.getClass().getMethods()).filter(m -> m.getName().startsWith("get")).forEach(g -> System.out.println(propertyName(g.getName())));
        return "";
    }

    private static String propertyName(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    public static void main(String[] args) {
        var person = new Person("John", "Doe");
        System.out.println(toJSON(person));
        var alien = new Alien("E.T.", 100);
        System.out.println(toJSON(alien));
    }
}
