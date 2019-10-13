package fr.umlv.java.inside;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {

    @Test @Tag("E1Q3")
    public void callaStaticHelloMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method = Example.class.getDeclaredMethod("aStaticHello", int.class);
        method.setAccessible(true);
        assertEquals("question 3", method.invoke(null, 3));
    }

    @Test @Tag("E1Q3")
    public void callanInstanceHelloMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        var method = Example.class.getDeclaredMethod("anInstanceHello", int.class);
        method.setAccessible(true);
        assertEquals("question 3", method.invoke(new Example(), 3));
    }

    @Test @Tag("E1Q4")
    public void newCallaStaticHelloMethod() throws Throwable {
        var lookup = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup());
        var mh = lookup.findStatic(Example.class, "aStaticHello", MethodType.methodType(String.class, int.class));
        assertEquals("question 4", (String) mh.invokeExact(4));
    }

    @Test @Tag("E1Q5")
    public void newCallanInstanceHelloMethod() throws Throwable {
        var lookup = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup());
        var mh = lookup.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
        assertEquals("question 5", (String) mh.invokeExact(new Example(), 5));
    }

    @Test @Tag("E1Q7")
    public void newCallanInstanceHelloMethodwithSetArguments() throws Throwable {
        var lookup = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup());
        var mh = lookup.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
        mh = MethodHandles.insertArguments(mh, 1, 8);
        mh = MethodHandles.dropArguments(mh, 1, int.class);
        assertEquals("question 7", (String) mh.invokeExact(new Example(), 9));
    }

    @Test @Tag("E1Q8")
    public void newCallanInstanceHelloMethodAsType() throws Throwable {
        var lookup = MethodHandles.privateLookupIn(Example.class, MethodHandles.lookup());
        var mh = lookup.findVirtual(Example.class, "anInstanceHello", MethodType.methodType(String.class, int.class));
        mh = mh.asType(MethodType.methodType(String.class, Example.class, Integer.class));
        assertEquals("question 8", (String) mh.invokeExact(new Example(), Integer.valueOf(8)));
    }

    @Test @Tag("E1Q9")
    public void newCallanInstanceHelloMethodConstant() throws Throwable {
        var mh = MethodHandles.constant(String.class, "question 8");
        assertEquals("question 8", (String) mh.invokeExact());
    }

    @Test @Tag("E1Q10")
    public void newCallanInstanceHelloMethodGuard() throws Throwable {
        var lookup = MethodHandles.lookup();
        var mhEqual = lookup.findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
        var mh = MethodHandles.guardWithTest(
                mhEqual.asType(MethodType.methodType(boolean.class, String.class, String.class)),
                MethodHandles.dropArguments(MethodHandles.constant(int.class, 1),0, String.class, String.class),
                MethodHandles.dropArguments(MethodHandles.constant(int.class, -1),0, String.class, String.class)
        );
        var secret = "foo";
        assertEquals(1, (int)mh.invokeExact(secret, "foo"));
    }
}