package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface Logger {
    public void log(String message);

    public static Logger of(Class<?> declaringClass, Consumer<? super String> consumer) {
        Objects.requireNonNull(declaringClass);
        Objects.requireNonNull(consumer);
        var mh = createLoggingMethodHandle(declaringClass, consumer);
        return new Logger() {
            @Override
            public void log(String message) {
                lambdaExecption(message, mh);
            }
        };
    }

    public static Logger fastOf(Class<?> declaringClass, Consumer<? super String> consumer) {
        Objects.requireNonNull(declaringClass);
        Objects.requireNonNull(consumer);
        var mh = createLoggingMethodHandle(declaringClass, consumer);
        return (message) -> {
            lambdaExecption(message, mh);
        };
    }

    static void lambdaExecption(String message, MethodHandle mh) {
        Objects.requireNonNull(message);
        try {
            mh.invokeExact(message);
        } catch(Throwable t) {
            if (t instanceof RuntimeException)
                throw (RuntimeException)t;
            if (t instanceof Error)
                throw (Error)t;
            throw new UndeclaredThrowableException(t);
        }
    }

    /*private static MethodHandle createLoggingMethodHandle(Class<?> declaringClass, Consumer<? super String> consumer) {
        MethodHandle mh;
        try {
            mh = MethodHandles.lookup().findVirtual(Consumer.class, "accept", MethodType.methodType(void.class, Object.class));
        } catch (NoSuchMethodException |  IllegalAccessException e) {
            throw new AssertionError(e);
        }
        mh = MethodHandles.insertArguments(mh, 0, consumer);
        return mh.asType(MethodType.methodType(void.class, String.class));
    }*/

    private static MethodHandle createLoggingMethodHandle(Class<?> declaringClass, Consumer<? super String> consumer) {
        MethodHandle mh, mh2;
        var lookup = MethodHandles.lookup();
        try {
            mh2 = ENABLE_CALLSITES.get(declaringClass).dynamicInvoker();
            mh = MethodHandles.lookup().findVirtual(Consumer.class, "accept", MethodType.methodType(void.class, Object.class));
        } catch (NoSuchMethodException |  IllegalAccessException e) {
            throw new AssertionError(e);
        }
        mh = mh.bindTo(consumer);
        //mh = MethodHandles.insertArguments(mh, 0, consumer);
        mh = mh.asType(MethodType.methodType(void.class, String.class));
        return MethodHandles.guardWithTest(mh2, mh, MethodHandles.empty(MethodType.methodType(void.class, String.class)));
    }

    static final ClassValue<MutableCallSite> ENABLE_CALLSITES = new ClassValue<MutableCallSite>() {
        protected MutableCallSite computeValue(Class<?> type) {
            var mutableCS = new MutableCallSite(MethodHandles.constant(boolean.class, true));
            var array = new MutableCallSite[] { mutableCS };
            MutableCallSite.syncAll(array);
            return mutableCS;
        }
    };

    public static void enable(Class<?> declaringClass, boolean enable) {
        ENABLE_CALLSITES.get(declaringClass).setTarget(MethodHandles.constant(boolean.class, enable));
    }

}

