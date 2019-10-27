package fr.umlv.java.inside;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Random;

public class StringSwitchBenchmark {
    private static ArrayList<String> list;
    static {
        String[] values= {"foo", "bar", "bazz", "aaa", "bonsoir", "test"};
        list = new ArrayList<>(1000000);
        Random r=new Random();
        int randomValue=r.nextInt(values.length);
        for(int i=0; i<1000000; i++)
            list.add(values[randomValue]);
    }

    @Benchmark
    public void stringSwitch1() {
        list.forEach(StringSwitchExample::stringSwitch);

    }

    @Benchmark
    public void stringSwitch2() {
        list.forEach(StringSwitchExample::stringSwitch2);

    }

    @Benchmark
    public void stringSwitch3() {
        list.forEach(StringSwitchExample::stringSwitch3);

    }
}
