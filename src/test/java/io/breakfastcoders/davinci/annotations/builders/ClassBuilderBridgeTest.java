package io.breakfastcoders.davinci.annotations.builders;

import io.breakfastcoders.davinci.annotations.SourceType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ClassBuilderBridgeTest {
    @Test
    public void shouldGetJsonBuilder() {
        ClassBuilder builder = ClassBuilderBridge.getBuilder(SourceType.JSON);
        assertThat(builder).isNotNull();
        assertThat(builder).isInstanceOf(JsonCodecClassBuilder.class);
    }
}
