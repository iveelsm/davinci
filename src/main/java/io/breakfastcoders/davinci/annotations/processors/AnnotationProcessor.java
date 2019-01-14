package io.breakfastcoders.davinci.annotations.processors;

import io.breakfastcoders.davinci.annotations.Codec;
import io.breakfastcoders.davinci.annotations.SourceType;
import io.breakfastcoders.davinci.serialization.Strategy;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

/**
 * An Annotation Processor is very limited in it's capabilities with the public APIs defined by Java.
 * As a result, you get limited control over what you can do with code generation.
 * It is intended that this library will eventually develop an UnsafeAnnotationProcessor that will use non-public APIs.
 * The goal is to follow in the footsteps of Lombok and do code generation within the annotated file.
 * The SafeAnnotationProcessor should always be safe to use unless noted otherwise.
 * It is confined to the standard public APIs.
 */
public abstract class AnnotationProcessor extends AbstractProcessor {
    public static final String STRATEGY_FIELD = "strategy";

    /**
     * This gets the {@link Strategy strategy} defined by the annotation.
     * It uses the {@link TypeMirror mirror} given by the annotation processor in order to parse out the {@link Strategy strategy} used.
     * There is further documentation on how this happens within the code.
     * As well as some links to the source of this approach. Warning: It is weird.
     * It is implied that anything passed in has the necessary annotations specified.
     *
     * @param e Element produced by the compiler at the given stage.
     *          Should be annotated with the relevant annotations.
     * @return Class name for the {@link Strategy strategy}
     * @throws ClassNotFoundException Indicates we don't have the class for {@link Strategy strategy} in the classpath.
     *                                Not sure if this is possible given the way this code works.
     *                                However, on the off chance it does get thrown, there is something seriously wrong.
     */
    @SuppressWarnings("unchecked")
    protected Class<? extends Strategy<?>> getStrategy(@NotNull Element e) throws ClassNotFoundException {
        Codec annotation = e.getAnnotation(Codec.class);
        Class ret;
        TypeMirror mirror;

        /*
         * Why do this?
         *
         * I think the Java Annotation API has some major issues.
         * One of those being that at the time of the getAnnotation(Class<A> clazz) call,
         * we received back an instance of TypeMirror.
         * TypeMirrors are unable to access the properties of the things that they are mirroring.
         * However, if you let it throw....
         * for some bizarre reason it will give you what you wanted in the exception. Pretty wild.
         *
         * See here for details on this:
         *   1. https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
         *   2. https://stackoverflow.com/questions/7687829/java-6-annotation-processing-getting-a-class-from-an-annotation
         */
        try {
            ret = annotation.strategy();
            return ret;
        } catch (MirroredTypeException ex) {
            mirror = ex.getTypeMirror();
        }

        ret = Class.forName(mirror.toString());
        return ret;
    }


    @SuppressWarnings("unchecked")
    protected SourceType getSourceType(@NotNull Element e) {
        Codec annotation = e.getAnnotation(Codec.class);
        SourceType ret;
        TypeMirror mirror;

        /*
         * Why do this?
         *
         * I think the Java Annotation API has some major issues.
         * One of those being that at the time of the getAnnotation(Class<A> clazz) call,
         * we received back an instance of TypeMirror.
         * TypeMirrors are unable to access the properties of the things that they are mirroring.
         * However, if you let it throw....
         * for some bizarre reason it will give you what you wanted in the exception. Pretty wild.
         *
         * See here for details on this:
         *   1. https://area-51.blog/2009/02/13/getting-class-values-from-annotations-in-an-annotationprocessor/
         *   2. https://stackoverflow.com/questions/7687829/java-6-annotation-processing-getting-a-class-from-an-annotation
         */
        try {
            ret = annotation.type();
            return ret;
        } catch (MirroredTypeException ex) {
            mirror = ex.getTypeMirror();
        }

        ret = SourceType.valueOf(mirror.toString());
        return ret;
    }

    /**
     * Gets all the annotated classes for the elements passed into the annotation processor.
     * Method annotations would not be processed here.
     *
     * @param annotatedElements Set of annotated elements from the compiler
     * @return Filtered list of elements specifying only those that are both annotated and classes
     */
    protected List<Element> getAnnotatedClasses(@NotNull Set<? extends Element> annotatedElements) {
        Map<Boolean, List<Element>> map = annotatedElements.stream()
                .collect(Collectors.partitioningBy(
                        element -> element.getKind().isClass()
                ));

        logImproperElements(map.get(false));
        return map.get(true);
    }

    /**
     * Logs elements that are not properly annotated.
     * This would occur if you annotated a method with the pertinent annotations.
     *
     * @param elements List of elements to notify the consumer of
     */
    protected abstract void logImproperElements(List<Element> elements);

    /**
     * Writes the class out to the given generated code location.
     *
     * @param filer           Object used for writing source code in the {@link javax.annotation.processing.ProcessingEnvironment environment}
     * @param className       Class name of the generated file
     * @param classDefinition String representing the class to write.
     *                        Should contain all generated code.
     */
    protected abstract void write(@NotNull final Filer filer, final String className, final String classDefinition);
}
