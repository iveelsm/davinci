package io.breakfastcoders.davinci.serialization.codecs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.breakfastcoders.davinci.serialization.Strategy;

/**
 * A mapper is an abstraction defines a unique set of properties that the underlying libraries use for serialization options.
 * It is is designed to abstract all the aspects of the an internal libraries in a simple to use format.
 * It has not achieved that goal up to this point.
 */
public interface Mapper {
    /**
     * Gets a {@link ObjectMapper mapper}.
     * Used by codecs in package, and tests outside the package
     * TODO: Abstract this away from Jackson towards simpler abstractions
     *
     * @return Underlying mapper strategy represented by a {@link ObjectMapper mapper}
     */
    ObjectMapper getMapper();

    /**
     * Gets the {@link Strategy strategy} used to construct the Mapper.
     *
     * @return A {@link Strategy strategy} representing the parameters associated with the Mapper
     */
    Strategy getStrategy();
}
