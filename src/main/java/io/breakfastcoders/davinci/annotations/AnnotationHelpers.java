package io.breakfastcoders.davinci.annotations;

import io.breakfastcoders.davinci.annotations.builders.ClassBuilder;
import org.jetbrains.annotations.NotNull;

import static io.breakfastcoders.davinci.annotations.AnnotationConstants.NUMBER_OF_SPACES;

/**
 * This class specifies helper methods for all types of Annotation Processors.
 * Typically used by {@link ClassBuilder builders}.
 */
public final class AnnotationHelpers {
    /**
     * Gets the package from a full class name.
     * Given io.breakfastcoders.davinci.annotation.BuilderHelpers
     * Should return io.breakfastcoders.davinci.annotations
     *
     * @param className Fully qualified class name
     * @return Package the class is in
     */
    @NotNull
    public static CharSequence getPackage(@NotNull String className) {
        int lastDot = className.lastIndexOf('.');
        return className.substring(0, lastDot);
    }

    /**
     * Gets the class from a fully qualified class string.
     * Given io.breakfastcoders.davinci.annotation.BuilderHelpers
     * Should return BuilderHelpers
     *
     * @param className Fully qualified class name
     * @return Class name
     */
    @NotNull
    public static CharSequence getRelativeName(@NotNull String className) {
        int lastDot = className.lastIndexOf('.');
        return className.substring(lastDot + 1);
    }


    /**
     * Formats a string for some readable generated code.
     *
     * @param toFormat String to format
     * @param tabs     Number of tabs to indent by
     * @return Formatted string
     */
    public static String formatString(String toFormat, int tabs) {
        return String.format("%s%s%n",
                buildSpacesString(tabs * NUMBER_OF_SPACES),
                toFormat
        );
    }

    @NotNull
    private static String buildSpacesString(int numSpaces) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < numSpaces; ++i) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
