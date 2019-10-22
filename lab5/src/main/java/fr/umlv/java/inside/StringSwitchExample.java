package fr.umlv.java.inside;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Objects;

public class StringSwitchExample {

    private static final MethodHandle STRING_EQUALS;

    static {
        var lookup = MethodHandles.lookup();
        try {
            STRING_EQUALS = lookup.findVirtual(String.class, "equals", MethodType.methodType(boolean.class, Object.class));
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    static int stringSwitch(String msg){
        switch (Objects.requireNonNull(msg)){
            case "foo": return 0;
            case "bar": return 1;
            case "bazz": return 2;
            default: return -1;
        }
    }

    static int stringSwitch2(String msg) {
        var mh = createMHFromStrings2("foo", "bar", "bazz");
        try{
            return (int) mh.invokeExact(msg);
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t){
            throw new UndeclaredThrowableException(t);
        }
    }

    private static MethodHandle createMHFromStrings2(String ...args) {
        var mh = MethodHandles.dropArguments(MethodHandles.constant(int.class, -1), 0, String.class);
        if (args.length == 0)
            return mh;
        for (var i = 0; i < args.length; i++){
            mh = MethodHandles.guardWithTest(
                    MethodHandles.insertArguments(STRING_EQUALS, 1, args[i]),
                    MethodHandles.dropArguments(MethodHandles.constant(int.class, i), 0, String.class),
                    mh
            );
        }
        return mh;
    }

    public static MethodHandle createMHFromStrings3(String... matches) {
        return new InliningCache(matches).dynamicInvoker();
    }

    static class InliningCache extends MutableCallSite {
        private static final MethodHandle SLOW_PATH;
        static {
            SLOW_PATH = ...
        }

        private final List<String> matches;

        public InliningCache(String... matches) {
            super(MethodType.methodType(int.class, String.class));
            this.matches = List.of(matches);
            setTarget(MethodHandles.insertArgument(SLOW_PATH, 0, this));
        }

        private MethodHandle slowPath(String value) {
            // TODO
        }
    }

}
