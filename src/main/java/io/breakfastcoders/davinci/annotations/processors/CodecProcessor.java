package io.breakfastcoders.davinci.annotations.processors;

import io.breakfastcoders.davinci.annotations.Codec;
import io.breakfastcoders.davinci.annotations.SourceType;
import io.breakfastcoders.davinci.annotations.builders.ClassBuilder;
import io.breakfastcoders.davinci.annotations.builders.ClassBuilderBridge;
import io.breakfastcoders.davinci.serialization.Strategy;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.jetbrains.annotations.NotNull;

import static io.breakfastcoders.davinci.annotations.AnnotationConstants.CODEC_CLASS;

/**
 * This processor is responsible for processing any classes annotated with {@link Codec codec}.
 * It handles any preprocessing and source code generation in accordance with the standard public APIs.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("io.breakfastcoders.davinci.annotations.Codec")
public class CodecProcessor extends AnnotationProcessor {
    private Messager messager;
    private Filer filer;

    /**
     * Initializes the environment and certain fields.
     * Called before starting the processing.
     *
     * @param env Environment of the annotation processing
     */
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        messager = env.getMessager();
        filer = env.getFiler();
    }

    /**
     * Processes annotated elements for the given round.
     *
     * @param annotations Annotations this class is responsible for.
     *                    Should be defined at the top level of the class.
     * @param roundEnv    The environment for the given round of the compilation.
     * @return True if we are done processing, false otherwise.
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);

            List<Element> annotatedClasses = getAnnotatedClasses(annotatedElements);

            annotatedClasses.forEach(this::processElement);
        }
        return true;
    }

    private void processElement(Element e) {
        try {
            String className = e.toString();
            Class<? extends Strategy<?>> strategy = getStrategy(e);
            SourceType type = getSourceType(e);
            ClassBuilder builder = ClassBuilderBridge.getBuilder(type);
            String classDefinition = builder.build(className, strategy);
            write(filer, className, classDefinition);
        } catch (Exception ex) {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(
                    "There was an issue processing the element %s. Exception: %s",
                    e.toString(), ex.toString()));
        }
    }

    @Override
    protected void logImproperElements(List<Element> elements) {
        elements.forEach(x ->
                messager.printMessage(
                        Diagnostic.Kind.WARNING,
                        String.format(
                                "Element %s was improperly annotated with %s annotation",
                                x.toString(), CODEC_CLASS)
                )
        );
    }

    @Override
    protected void write(@NotNull final Filer filer, final String className, final String classDefinition) {
        String builderClassName = className + "Builder";
        try {
            JavaFileObject builderFile = filer.createSourceFile(builderClassName);

            try (PrintWriter writer = new PrintWriter(builderFile.openWriter())) {
                writer.write(classDefinition);
            }
        } catch (IOException ex) {
            messager.printMessage(Diagnostic.Kind.ERROR, String.format(
                    "There was an issue writing the file for the class %s. Exception: %s",
                    builderClassName, ex.toString()));
        }
    }
}
