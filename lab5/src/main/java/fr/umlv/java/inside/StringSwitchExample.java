package fr.umlv.java.inside;

import java.util.Objects;

public class StringSwitchExample {

    static int stringSwitch(String msg){
        switch (Objects.requireNonNull(msg)){
            case "foo": return 0;
            case "bar": return 1;
            case "bazz": return 2;
            default: return -1;
        }
    }
}
