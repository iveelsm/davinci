package io.breakfastcoders.davinci.serialization.codecs.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.Mapper;
import io.breakfastcoders.davinci.serialization.library.JacksonLibrary;
import org.jetbrains.annotations.NotNull;

/**
 * A mapper is an abstraction that usually contains a {@link ObjectMapper object mapper}.
 * In addition, it typically adds a set of unique properties.
 *
 * <p>JSON based {@link Mapper mappers} include a single {@link Strategy strategy}.
 * These {@link Strategy strategies} are used for unique identification.</p>
 */
class JsonObjectMapper implements Mapper {
    private final ObjectMapper mapper;
    private final Strategy strategy;

    /**
     * Implicitly constructs a new {@link ObjectMapper mapper} as a part of the call.
     * This is maintained in the objects state for the lifecycle of the object.
     *
     * @param strategy Strategy being used to construct the object.
     */
    JsonObjectMapper(@NotNull Strategy<JacksonLibrary> strategy) {
        this.strategy = strategy;
        this.mapper = new ObjectMapper();
        this.mapper.setPropertyNamingStrategy(strategy.getMappingStrategy().getStrategy());
    }

    /**
     * Returns the constructed mapper from the static initialization block.
     * <p>
     * Properties:
     * </p>
     * <ul>
     *   <p>
     *     <li>
     *       The {@link Strategy Strategy} being used in the map
     *     </li>
     *   </p>
     * </ul>
     *
     * @return Instance of ObjectMapper with the above properties
     */
    @Override
    public ObjectMapper getMapper() {
        return this.mapper;
    }

    @Override
    public Strategy getStrategy() {
        return this.strategy;
    }
}
