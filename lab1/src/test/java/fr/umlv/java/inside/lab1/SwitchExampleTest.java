package fr.umlv.java.inside.lab1;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SwitchExampleTest {

    @Test @Tag("Test1")
    public void TestSwitchDog() {
        assertEquals(1, SwitchExample.switchExample("dog"));
    }
    @Test @Tag("Test2")
    public void TestSwitchCat() {
        assertEquals(2, SwitchExample.switchExample("cat"));
    }
    @Test @Tag("Test3")
    public void TestSwitchFish() {
        assertEquals(4, SwitchExample.switchExample("fish"));
    }
    @Test @Tag("Test4")
    public void TestSwitchNull() {
        assertThrows(NullPointerException.class, () -> SwitchExample.switchExample(null));
    }

    @Test @Tag("Test5")
    public void TestSwitch2Dog() {
        assertEquals(1, SwitchExample.switchExample2("dog"));
    }
    @Test @Tag("Test6")
    public void TestSwitch2Cat() {
        assertEquals(2, SwitchExample.switchExample2("cat"));
    }
    @Test @Tag("Test7")
    public void TestSwitch2Fish() {
        assertEquals(4, SwitchExample.switchExample2("fish"));
    }
    @Test @Tag("Test8")
    public void TestSwitch2Null() {
        assertThrows(NullPointerException.class, () -> SwitchExample.switchExample2(null));
    }
}