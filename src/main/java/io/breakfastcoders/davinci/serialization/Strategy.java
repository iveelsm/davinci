package io.breakfastcoders.davinci.serialization;

import io.breakfastcoders.davinci.serialization.library.Library;

/**
 * A strategy is a means of abstracting all the serialization possibilities.
 * This includes everything from an interface for Jackson, GSON and other underlying libraries.
 * The Strategy interface is designed to be extended,
 * So that downstream implementation can invent their own methods of serialization.
 *
 * @param <T> Usually defined by the serialization technique. This may not be necessary.
 *            And furthermore it may leak the internal libraries usage under the hood.
 */
public interface Strategy<T extends Library> {
    /**
     * This is probably a poorly named method.
     * This should be the class that contains all the information for the libraries to do their processing.
     *
     * @return Instance of class that indicates the pertinent methods for library processing.
     */
    T getMappingStrategy();
}
