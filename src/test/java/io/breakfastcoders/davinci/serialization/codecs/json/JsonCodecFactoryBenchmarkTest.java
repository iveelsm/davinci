package io.breakfastcoders.davinci.serialization.codecs.json;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import io.breakfastcoders.davinci.utils.BenchmarkRun;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.rules.Stopwatch;

import static io.breakfastcoders.davinci.utils.Strategies.getStrategies;

public class JsonCodecFactoryBenchmarkTest {

    private static final Logger log = LoggerFactory.getLogger(JsonCodecFactoryBenchmarkTest.class);
    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
    };
    private JsonCodecFactory factory = JsonCodecFactory.getInstance();
    private int interval = 1_000_000;
    private int groupSize = 20;

    @BeforeClass
    public static void classInitialize() {

    }

    @Before
    public void initialize() {
        factory.clear();
    }

    @Test
    public void getCallsShouldBeFast() {
        log.info(() -> "\nWarming up...\n\n");
        run();

        log.info(() -> "\nSecond run...\n\n");
        run();
    }

    @Test
    public void variedGetCallsShouldBeFast() {
        log.info(() -> "\nWarming up...\n\n");
        run(getStrategies());

        log.info(() -> "\nSecond run...\n\n");
        run(getStrategies());
    }

    private void run() {
        SnakeCaseStrategy strategy = new SnakeCaseStrategy();
        BenchmarkRun.run(groupSize, interval, () -> factory.getCodec(strategy), stopwatch);
    }

    private void run(List<Strategy> strategies) {
        BenchmarkRun.run(strategies, interval, factory, stopwatch);
    }
}
