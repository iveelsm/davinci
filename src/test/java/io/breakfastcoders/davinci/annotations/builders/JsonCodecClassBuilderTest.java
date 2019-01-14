package io.breakfastcoders.davinci.annotations.builders;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import java.io.IOException;
import org.junit.Test;

import static io.breakfastcoders.davinci.utils.TestUtilities.getResourceString;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonCodecClassBuilderTest {
    private JsonCodecClassBuilder builder = new JsonCodecClassBuilder();

    @Test
    public void shouldBuildClassProperly() throws IOException {
        Class<? extends Strategy<?>> strategy = SnakeCaseStrategy.class;
        String className = "com.ibotta.test.Test";
        String classDefinition = builder.build(className, strategy);
        assertThat(classDefinition).isEqualTo(getResourceString(
                this.getClass().getClassLoader(),
                "class/TestClass"));
    }
}
