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

    private static final ClassValue<Method[]> cv =new ClassValue<>() {
        @Override
        protected Method[] computeValue(Class<?> type) {
            return type.getMethods();
        }
    };

    public static String toJSON(Object obj){
        return Arrays.stream(cv.get(obj.getClass()))
                .filter(Json::isCorrect)
                .sorted(Comparator.comparing(Method::getName))
                .map(m -> getDisplayName(m)+" : "+formatData(m, obj))
                .collect(joining(",", "{","}"));
    }

    private static boolean isCorrect(Method m){
        return m.getName().startsWith("get") && m.isAnnotationPresent(JSONProperty.class);
    }

    private static String propertyName(String name) {
        return Character.toLowerCase(name.charAt(3)) + name.substring(4);
    }

    private static String getDisplayName(Method m){
        String value = m.getAnnotation(JSONProperty.class).value();
        return value.isEmpty()?propertyName(m.getName()):value;
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
