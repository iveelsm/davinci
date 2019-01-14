package io.breakfastcoders.davinci.serialization.strategy;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.library.JacksonLibrary;

/**
 * This strategy provides the instructions for.
 * <ul>
 *     <li>
 *         Serializing objects from Kebab Case sources.
 *     </li>
 *     <li>
 *          Serializing objects into Kebab Case outputs.
 *     </li>
 * </ul>
 */
public class KebabCaseStrategy implements Strategy<JacksonLibrary> {
    private final JacksonLibrary library = new JacksonLibrary(PropertyNamingStrategy.KEBAB_CASE);

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
