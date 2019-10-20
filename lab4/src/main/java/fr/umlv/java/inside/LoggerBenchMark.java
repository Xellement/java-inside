package fr.umlv.java.inside;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class LoggerBenchMark {

    private static class LoggerClass {
        public static final Logger LOGGER = Logger.of(LoggerClass.class, __ -> {});
    }

    private static class FastLoggerClass {
        public static final Logger LOGGER = Logger.fastOf(LoggerClass.class, __ -> {});
    }

    @Benchmark
    public void no_op() {
        // empty
    }

    @Benchmark
    public void simple_logger() {
        LoggerClass.LOGGER.log("LOL");
    }

    @Benchmark
    public void simpleFast_logger() {
        FastLoggerClass.LOGGER.log("LOL");
    }

    @Benchmark
    public void simple_logger_fastOf_disable() {
        FastLoggerClass.LOGGER.log("LOL");
        Logger.enable(FastLoggerClass.class, false);
    }

    @Benchmark
    public void simple_logger_fastof_enable() {
        FastLoggerClass.LOGGER.log("LOL");
        Logger.enable(FastLoggerClass.class, true);
    }
}
