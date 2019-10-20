package fr.umlv.java.inside;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    @Test @Tag("Q2")
    public void testLogger() {
        Foo.LOGGER.log("LOL");
        assertEquals("LOL", Foo.SB.toString());
    }

    @Test @Tag("Q2")
    public void ofNull() {
        assertAll(
                () -> assertThrows(NullPointerException.class , () -> Logger.of(null, __ -> { }).log("")),
                () -> assertThrows(NullPointerException.class , () -> Logger.of(LoggerTest.class, null).log(""))
        );
    }

    @Test @Tag("Q2")
    public void logNull() {
        assertThrows(NullPointerException.class, () -> Foo.LOGGER.log(null));
    }

    @Test @Tag("Q5")
    public void testFastLogger() {
        Foo.LOGGER.log("LOL");
        assertEquals("LOL", LoggerTest.Foo.SB.toString());
    }

    @Test @Tag("Q5")
    public void ofFastNull() {
        assertAll(() -> assertThrows(NullPointerException.class, () -> Logger.of(FooFast.class, null).log("")),
                () -> assertThrows(NullPointerException.class, () -> Logger.of(null, __ -> {
                }).log("")));
    }

    @Test @Tag("Q5")
    public void logFastNull() {
        assertThrows(NullPointerException.class, () -> FooFast.LOGGER.log(null));
    }

    @Test @Tag("Q7")
    public void unableLogger() {
        Logger.enable(FooFast.class, false);
        FooFast.LOGGER.log("LOL");
        assertEquals("", LoggerTest.FooFast.SB.toString());
    }

    private static class Foo {
        private static final StringBuilder SB = new StringBuilder();
        public static final Logger LOGGER = Logger.of(Foo.class, msg->{
            SB.setLength(0);
            SB.append(msg);
        });
    }

    private static class FooFast {
        private static final StringBuilder SB = new StringBuilder();
        public static final Logger LOGGER = Logger.fastOf(Foo.class, msg->{
            SB.setLength(0);
            SB.append(msg);
        });
    }
}