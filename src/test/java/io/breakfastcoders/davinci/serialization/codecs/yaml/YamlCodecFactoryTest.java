package io.breakfastcoders.davinci.serialization.codecs.yaml;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class YamlCodecFactoryTest {
    private YamlCodecFactory factory = YamlCodecFactory.getInstance();

    @Before
    public void init() {
        factory.clear();
    }

    @Test
    public void shouldCreateCodecWithStrategy() {
        Strategy strategy = new KebabCaseStrategy();
        YamlCodec codec = factory.getCodec(strategy);
        assertThat(codec.getCodec().getStrategy()).isEqualTo(strategy);
    }

    @Test
    public void shouldHandleTheSameCodecCall() {
        Strategy strategy = new KebabCaseStrategy();
        YamlCodec codec1 = factory.getCodec(strategy);
        YamlCodec codec2 = factory.getCodec(strategy);
        YamlCodec codec3 = factory.getCodec(strategy);
        assertThat(codec1).isEqualTo(codec2).isEqualTo(codec3);
    }
}
