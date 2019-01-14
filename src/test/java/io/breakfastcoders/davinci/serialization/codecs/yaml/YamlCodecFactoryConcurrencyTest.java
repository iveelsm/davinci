package io.breakfastcoders.davinci.serialization.codecs.yaml;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.utils.ConcurrentAccess;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static io.breakfastcoders.davinci.utils.Strategies.getRandomStrategy;
import static io.breakfastcoders.davinci.utils.Strategies.getStrategies;
import static org.assertj.core.api.Assertions.assertThat;


public class YamlCodecFactoryConcurrencyTest {
    public final YamlCodecFactory factory = YamlCodecFactory.getInstance();

    @Before
    public void initialize() {
        factory.clear();
        for (Strategy strategy : getStrategies()) {
            factory.getCodec(strategy);
        }
    }

    @Test
    public void shouldHandleConcurrentAccess() throws Exception {
        int numTries = 5;
        for (int j = 0; j < numTries; ++j) {
            int numThreads = 1_000;
            List<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i < numThreads; ++i) {
                runnables.add(() -> {
                    factory.clear();
                    YamlCodec result = factory.getCodec(getRandomStrategy());
                    assertThat(result).isNotNull();
                });
            }

            ConcurrentAccess.assertConcurrent("JsonCodecFactory access", runnables, 60);
        }
    }
}
