package io.breakfastcoders.davinci.annotations.processors;

import com.google.testing.compile.CompilationRule;
import io.breakfastcoders.davinci.annotations.SourceType;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnnotationProcessorTest {
    @Rule
    public CompilationRule rule = new CompilationRule();
    private Elements elements;
    private AnnotationProcessor processor;

    @Before
    public void setup() {
      elements = rule.getElements();
      processor = new CodecProcessor();
    }

    @Test
    public void shouldGetStrategyFromAnnotation() throws ClassNotFoundException {
        Element e = elements.getTypeElement("io.breakfastcoders.davinci.utils.annotations.ComplexTestModel");
        Class<? extends Strategy<?>> strategy = processor.getStrategy(e);
        assertThat(strategy).isEqualTo(KebabCaseStrategy.class);
    }

    @Test
    public void shouldGetSourceTypeFromElement() {
      Element e = elements.getTypeElement("io.breakfastcoders.davinci.utils.annotations.ComplexTestModel");
      SourceType type = processor.getSourceType(e);
      assertThat(type).isEqualTo(SourceType.JSON);
    }

    @Test
    public void shouldThrowOnBadElement() {
      Element e = elements.getTypeElement("io.breakfastcoders.davinci.utils.codecs.ComplexTestModel");
      assertThatThrownBy(() -> processor.getStrategy(e))
          .isInstanceOf(NullPointerException.class);
    }
}
