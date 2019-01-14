package io.breakfastcoders.davinci.serialization.strategy;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.library.JacksonLibrary;

/**
 * This strategy provides the instructions for.
 * <ul>
 *     <li>
 *         Serializing objects from Snake Case sources.
 *     </li>
 *     <li>
 *          Serializing objects into Snake Case outputs.
 *     </li>
 * </ul>
 */
public class SnakeCaseStrategy implements Strategy<JacksonLibrary> {
    private final JacksonLibrary library = new JacksonLibrary(PropertyNamingStrategy.SNAKE_CASE);

    /**
     * Instructions for serialization for the underlying libraries.
     *
     * @return An object representation how to serialize and deserialize data types
     */
    @Override
    public JacksonLibrary getMappingStrategy() {
        return this.library;
    }
}
