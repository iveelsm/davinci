package io.breakfastcoders.davinci.annotations.builders;

import com.google.errorprone.annotations.Immutable;
import io.breakfastcoders.davinci.serialization.Strategy;
import org.jetbrains.annotations.NotNull;

/**
 * A Class Builder is responsible for creating a representation of a class for the processors.
 * This representation is typically defined by a String for the file contents.
 */
@Immutable
public interface ClassBuilder {
    /**
     * Builds a string representing the class contents.
     * Generates any methods, imports, package declarations or class declarations necessary.
     *
     * @param className Class name to construct for
     * @param strategy Strategy to use in the generated methods
     * @return String representing class contents
     */
    String build(String className, @NotNull Class<? extends Strategy<?>> strategy);
}
