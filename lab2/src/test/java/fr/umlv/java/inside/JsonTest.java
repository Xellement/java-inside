package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static fr.umlv.java.inside.Json.toJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    @Test
    public void toJSONTestPerson() {
        var person = new Person("John", "Doe");
        for (int i = 0; i < 10000000; i++) {
            assertEquals("{First-Name : John,lastName : Doe}", toJSON(person));
        }


    }

    @Test
    public void toJSONTestAlien() {
        var alien = new Alien("E.T.", 100);
        assertEquals("{age : 100,planet : E.T.}", toJSON(alien));
    }

    @Test
    public void toJSONTest2Person() {
        var person = new Person("John", "Doe");
        assertEquals("{First-Name : John,lastName : Doe}", toJSON(person));
    }

    @Test
    public void toJSONTest2Alien() {
        var alien = new Alien("E.T.", 100);
        assertEquals("{age : 100,planet : E.T.}", toJSON(alien));
    }

    public static class Person {
        private final String firstName;
        private final String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = Objects.requireNonNull(firstName);
            this.lastName = Objects.requireNonNull(lastName);
        }

        @JSONProperty("First-Name")
        public String getFirstName() {
            return firstName;
        }
        @JSONProperty
        public String getLastName() {
            return lastName;
        }
    }
    public static class Alien {
        private final String planet;
        private final int age;

        public Alien(String planet, int age) {
            if (age <= 0) {
                throw new IllegalArgumentException("Too young...");
            }
            this.planet = Objects.requireNonNull(planet);
            this.age = age;
        }
        @JSONProperty
        public String getPlanet() {
            return planet;
        }
        @JSONProperty
        public int getAge() {
            return age;
        }
    }

}
