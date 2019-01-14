package io.breakfastcoders.davinci.annotations.processors;

import com.google.testing.compile.CompilationRule;
import java.io.IOException;
import javax.tools.JavaFileObject;
import org.junit.Rule;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static io.breakfastcoders.davinci.utils.TestUtilities.constructFileObjectForResource;

public class CodecProcessorTest {
    @Rule
    public CompilationRule rule = new CompilationRule();

    private CodecProcessor processor = new CodecProcessor();


    @Test
    public void shouldCompile() throws IOException {
        JavaFileObject source = constructFileObjectForResource(this.getClass().getClassLoader(),
                "class/success/ComplexTestModel",
                "class/success/ComplexTestModel");

        assertAbout(javaSource())
                .that(source)
                .withCompilerOptions("-Xlint:-processing")
                .processedWith(processor)
                .compilesWithoutWarnings();
    }

    @Test
    public void shouldCompileAndBuildSource() throws IOException {
        JavaFileObject source = constructFileObjectForResource(this.getClass().getClassLoader(),
                "class/success/ComplexTestModel",
                "class/success/ComplexTestModel");
        JavaFileObject builtSource = constructFileObjectForResource(this.getClass().getClassLoader(),
                "class/success/ComplexTestModelBuilder",
                "class/success/ComplexTestModelBuilder");

        assertAbout(javaSource())
                .that(source)
                .withCompilerOptions("-Xlint:-processing")
                .processedWith(processor)
                .compilesWithoutWarnings()
                .and()
                .generatesSources(builtSource);
    }

    @Test
    public void shouldFailToConstructBadStrategy() throws IOException {
        JavaFileObject source = constructFileObjectForResource(this.getClass().getClassLoader(),
                "class/fail/BadStrategyComplexTestModel",
                "class/fail/BadStrategyComplexTestModel");

        assertAbout(javaSource())
                .that(source)
                .withCompilerOptions("-Xlint:-processing")
                .processedWith(processor)
                .failsToCompile();
    }
}
