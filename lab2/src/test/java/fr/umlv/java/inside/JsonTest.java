package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;

import static fr.umlv.java.inside.Json.toJSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    @Test
    public void toJSONTestPerson() {
        var person = new Person("John", "Doe");
        assertEquals("{class : class fr.umlv.java.inside.Person,firstName : John,lastName : Doe}", toJSON(person));
    }

    @Test
    public void toJSONTestAlien() {
        var alien = new Alien("E.T.", 100);
        assertEquals("{age : 100,class : class fr.umlv.java.inside.Alien,planet : E.T.}", toJSON(alien));
    }

}
