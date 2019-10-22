package fr.umlv.java.inside;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StringSwitchExampleTests {

    @Test
    void MultipleTestStringSwitch(){
        assertAll(
                () -> assertThrows(NullPointerException.class, ()->StringSwitchExample.stringSwitch(null)),
                () -> assertEquals(0, StringSwitchExample.stringSwitch("foo")),
                () -> assertEquals(1, StringSwitchExample.stringSwitch("bar")),
                () -> assertEquals(2, StringSwitchExample.stringSwitch("bazz")),
                () -> assertEquals(-1, StringSwitchExample.stringSwitch("abab"))
        );
    }

    @ParameterizedTest
    @MethodSource("stringSwitchProvider")
    void MultipleTestStringSwitchWithProvider(ToIntFunction<String> fun) {
        assertAll(
                () -> assertThrows(NullPointerException.class, ()->fun.applyAsInt(null)),
                () -> assertEquals(0, fun.applyAsInt("foo")),
                () -> assertEquals(1, fun.applyAsInt("bar")),
                () -> assertEquals(2, fun.applyAsInt("bazz")),
                () -> assertEquals(-1, fun.applyAsInt("abab"))
        );
    }

    static Stream<ToIntFunction<String>> stringSwitchProvider() {
        return Stream.of(StringSwitchExample::stringSwitch, StringSwitchExample::stringSwitch2);
    }

}
