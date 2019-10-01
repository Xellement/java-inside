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
        return Arrays.stream(obj.getClass().getMethods())
                .filter(m -> m.getName().startsWith("get") && m.isAnnotationPresent(JSONProperty.class))
                .sorted(Comparator.comparing(Method::getName))
                .map(m -> propertyName(m.getName())+" : "+formatData(m, obj))
                .collect(joining(",", "{","}"));
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

}
