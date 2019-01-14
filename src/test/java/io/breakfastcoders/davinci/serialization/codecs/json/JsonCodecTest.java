package io.breakfastcoders.davinci.serialization.codecs.json;

import io.breakfastcoders.davinci.error.ExceptionStates;
import io.breakfastcoders.davinci.error.ParsingException;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.PascalCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import io.breakfastcoders.davinci.utils.codecs.ComplexTestModel;
import io.breakfastcoders.davinci.utils.codecs.SimpleTestModel;
import java.io.IOException;
import org.junit.Test;

import static io.breakfastcoders.davinci.utils.StringUtils.flattenString;
import static io.breakfastcoders.davinci.utils.TestUtilities.getResourceString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JsonCodecTest {
    private JsonCodecFactory factory = JsonCodecFactory.getInstance();
    private ClassLoader loader = this.getClass().getClassLoader();

    @Test
    public void shouldHandleComplexSnakeCase() throws IOException {
        JsonCodec codec = factory.getCodec(new SnakeCaseStrategy());
        ComplexTestModel model = codec.decode(getResourceString(loader, "json/snake/complex_test.json"), ComplexTestModel.class);
        assertThat(model).isNotNull();
        assertThat(model.getSubClassList().get(0).getDeeper().getValue()).isEqualTo("1");
    }

    @Test
    public void shouldHandleSimpleSnakeCase() throws IOException {
        JsonCodec codec = factory.getCodec(new SnakeCaseStrategy());
        SimpleTestModel model = codec.decode(getResourceString(loader, "json/snake/simple_test.json"), SimpleTestModel.class);
        assertThat(model).isNotNull();
        assertThat(model.doubleValue).isEqualTo(1.23);
    }

    @Test
    public void shouldSerializeTwoDifferentWays() throws IOException {
        JsonCodec codec1 = factory.getCodec(new SnakeCaseStrategy());
        JsonCodec codec2 = factory.getCodec(new KebabCaseStrategy());
        SimpleTestModel model = codec1.decode(getResourceString(loader, "json/snake/simple_test.json"), SimpleTestModel.class);
        assertThat(model).isNotNull();
        String result = codec2.encode(model);
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldHandleNullData() {
        JsonCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(null, ComplexTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.NULL_DATA.getMessage());
    }

    @Test
    public void shouldHandleMalformedJson() {
        JsonCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(getResourceString(loader, "json/failure/malformed.json"), SimpleTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.MALFORMED_CONTENT.getMessage());
    }

    @Test
    public void shouldHandleConversionToWrongClass() {
        JsonCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(getResourceString(loader, "json/snake/complex_test.json"), SimpleTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.MALFORMED_CONTENT.getMessage());
    }

    @Test
    public void shouldEncodeAndDecodeComplexSnake() throws  IOException {
      JsonCodec codec1 = factory.getCodec(new SnakeCaseStrategy());
      String resourceString = getResourceString(loader, "json/snake/complex_test.json");
      ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
      assertThat(model).isNotNull();
      String flattenedString = flattenString(resourceString);
      assertThat(flattenedString).isEqualTo(codec1.encode(model));
    }

  @Test
  public void shouldEncodeAndDecodeComplexKebab() throws  IOException {
    JsonCodec codec1 = factory.getCodec(new KebabCaseStrategy());
    String resourceString = getResourceString(loader, "json/kebab/complex_test.json");
    ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = flattenString(resourceString);
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeComplexPascal() throws  IOException {
    JsonCodec codec1 = factory.getCodec(new PascalCaseStrategy());
    String resourceString = getResourceString(loader, "json/pascal/complex_test.json");
    ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = flattenString(resourceString);
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimpleSnake() throws  IOException {
    JsonCodec codec1 = factory.getCodec(new SnakeCaseStrategy());
    String resourceString = getResourceString(loader, "json/snake/simple_test.json");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = flattenString(resourceString);
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimpleKebab() throws  IOException {
    JsonCodec codec1 = factory.getCodec(new KebabCaseStrategy());
    String resourceString = getResourceString(loader, "json/kebab/simple_test.json");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = flattenString(resourceString);
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimplePascal() throws  IOException {
    JsonCodec codec1 = factory.getCodec(new PascalCaseStrategy());
    String resourceString = getResourceString(loader, "json/pascal/simple_test.json");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = flattenString(resourceString);
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }
}
