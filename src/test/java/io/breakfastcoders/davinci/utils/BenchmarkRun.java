package io.breakfastcoders.davinci.utils;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.CodecFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.rules.Stopwatch;

public class BenchmarkRun {
    private static final Logger log = LoggerFactory.getLogger(BenchmarkRun.class);

    public static void run(int groupSize, int interval, Runnable run, Stopwatch stopwatch) {
        long currentNanos = 0;
        for (int i = 0; i < groupSize; ++i) {
            for (int j = 0; j < interval; ++j) {
                run.run();
            }
            logResults(interval, stopwatch.runtime(TimeUnit.NANOSECONDS) - currentNanos);
            currentNanos = stopwatch.runtime(TimeUnit.NANOSECONDS);
        }
    }

    @SuppressWarnings("unchecked")
    public static void run(@NotNull List<Strategy> strategies, int interval, CodecFactory factory, Stopwatch stopwatch) {
        long currentNanos = 0;
        for (Strategy strategy : strategies) {
            for (int j = 0; j < interval; ++j) {
                factory.getCodec(strategy);
            }
            logResults(interval, stopwatch.runtime(TimeUnit.NANOSECONDS) - currentNanos);
            currentNanos = stopwatch.runtime(TimeUnit.NANOSECONDS);
        }
    }

    private static void logResults(int number, long time) {
        log.info(() -> {
            return String.format("Nanoseconds per call: %s", time / number);
        });
    }
}
