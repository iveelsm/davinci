package io.breakfastcoders.davinci.annotations;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BuilderHelpersTest {

    @Test
    public void shouldGetPackageName() {
        String className = this.getClass().getName();
        String packageName = AnnotationHelpers.getPackage(className).toString();
        assertThat(packageName).isEqualTo("io.breakfastcoders.davinci.annotations");
    }

    @Test
    public void shouldGetRelativeName() {
        String className = this.getClass().getName();
        String relativeClassName = AnnotationHelpers.getRelativeName(className).toString();
        assertThat(relativeClassName).isEqualTo("BuilderHelpersTest");
    }
}
