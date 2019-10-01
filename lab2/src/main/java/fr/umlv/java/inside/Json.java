package fr.umlv.java.inside;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Comparator;

import static java.util.stream.Collectors.joining;

public class Json {
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
        var list = Arrays.stream(obj.getClass().getMethods())
                .filter(m -> m.getName().startsWith("get"))
                .sorted(Comparator.comparing(Method::getName))
                .map(m -> propertyName(m.getName())+" : "+formatData(m, obj))
                .collect(joining(",", "{","}"));
        System.out.println(list);
        return list;
    }

    private static String propertyName(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private static Object formatData(Method method, Object obj){
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            var cause = e.getCause();
            if (cause instanceof RuntimeException)
                throw (RuntimeException) cause;
            throw  new UndeclaredThrowableException(cause);
        }
    }

    public static void main(String[] args) {
        var person = new Person("John", "Doe");
        System.out.println(toJSON(person));
        var alien = new Alien("E.T.", 100);
        System.out.println(toJSON(alien));
    }

}
