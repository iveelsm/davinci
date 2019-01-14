package io.breakfastcoders.davinci.serialization.codecs.yaml;

import io.breakfastcoders.davinci.error.ExceptionStates;
import io.breakfastcoders.davinci.error.ParsingException;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.PascalCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import io.breakfastcoders.davinci.utils.codecs.ComplexTestModel;
import io.breakfastcoders.davinci.utils.codecs.SimpleTestModel;
import java.io.IOException;
import org.junit.Test;

import static io.breakfastcoders.davinci.utils.TestUtilities.getResourceString;
import static io.breakfastcoders.davinci.utils.TestUtilities.getUri;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class YamlCodecTest {
    private YamlCodecFactory factory = YamlCodecFactory.getInstance();
    private ClassLoader loader = this.getClass().getClassLoader();


    @Test
    public void shouldHandleComplexSnakeCase() throws IOException {
        YamlCodec codec = factory.getCodec(new SnakeCaseStrategy());
        ComplexTestModel model = codec.decode(getUri(loader, "yaml/snake/complex_test.yaml"), ComplexTestModel.class);
        assertThat(model).isNotNull();
        assertThat(model.getSubClassList().get(0).getDeeper().getValue()).isEqualTo("1");
    }

    @Test
    public void shouldHandleSimpleSnakeCase() throws IOException {
        YamlCodec codec = factory.getCodec(new SnakeCaseStrategy());
        SimpleTestModel model = codec.decode(getUri(this.getClass().getClassLoader(), "yaml/snake/simple_test.yaml"), SimpleTestModel.class);
        assertThat(model).isNotNull();
        assertThat(model.doubleValue).isEqualTo(1.23);
    }

    @Test
    public void shouldHandleNullData() {
        YamlCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(null, ComplexTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.NULL_DATA.getMessage());
    }

    @Test
    public void shouldHandleMalformedJson() {
        YamlCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(getUri(loader, "yaml/failure/malformed.yaml"), SimpleTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.INTERRUPTED_OPERATION.getMessage());
    }

    @Test
    public void shouldHandleConversionToWrongClass() {
        YamlCodec codec = factory.getCodec(new SnakeCaseStrategy());
        assertThatThrownBy(() -> codec.decode(getResourceString(loader, "yaml/snake/complex_test.yaml"), SimpleTestModel.class))
                .isInstanceOf(ParsingException.class)
                .hasMessage(ExceptionStates.INTERRUPTED_OPERATION.getMessage());
    }

  @Test
  public void shouldEncodeAndDecodeComplexSnake() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new SnakeCaseStrategy());
    String resourceString = getUri(loader, "yaml/snake/complex_test.yaml");
    ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/snake/complex_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeComplexKebab() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new KebabCaseStrategy());
    String resourceString = getUri(loader, "yaml/kebab/complex_test.yaml");
    ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/kebab/complex_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeComplexPascal() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new PascalCaseStrategy());
    String resourceString = getUri(loader, "yaml/pascal/complex_test.yaml");
    ComplexTestModel model = codec1.decode(resourceString, ComplexTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/pascal/complex_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimpleSnake() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new SnakeCaseStrategy());
    String resourceString = getUri(loader, "yaml/snake/simple_test.yaml");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/snake/simple_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimpleKebab() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new KebabCaseStrategy());
    String resourceString = getUri(loader, "yaml/kebab/simple_test.yaml");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/kebab/simple_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }

  @Test
  public void shouldEncodeAndDecodeSimplePascal() throws  IOException {
    YamlCodec codec1 = factory.getCodec(new PascalCaseStrategy());
    String resourceString = getUri(loader, "yaml/pascal/simple_test.yaml");
    SimpleTestModel model = codec1.decode(resourceString, SimpleTestModel.class);
    assertThat(model).isNotNull();
    String flattenedString = getResourceString(loader, "yaml/pascal/simple_test.yaml");
    assertThat(flattenedString).isEqualTo(codec1.encode(model));
  }
}
