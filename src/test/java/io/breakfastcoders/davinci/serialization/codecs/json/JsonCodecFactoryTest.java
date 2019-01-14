package io.breakfastcoders.davinci.serialization.codecs.json;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonCodecFactoryTest {
    private JsonCodecFactory factory = JsonCodecFactory.getInstance();

    @Before
    public void init() {
        factory.clear();
    }

    @Test
    public void shouldCreateCodecWithStrategy() {
        Strategy strategy = new KebabCaseStrategy();
        JsonCodec codec = factory.getCodec(strategy);
        assertThat(codec.getCodec().getStrategy()).isEqualTo(strategy);
    }

    @Test
    public void shouldHandleTheSameCodecCall() {
        Strategy strategy = new KebabCaseStrategy();
        JsonCodec codec1 = factory.getCodec(strategy);
        JsonCodec codec2 = factory.getCodec(strategy);
        JsonCodec codec3 = factory.getCodec(strategy);
        assertThat(codec1).isEqualTo(codec2).isEqualTo(codec3);
    }
}
