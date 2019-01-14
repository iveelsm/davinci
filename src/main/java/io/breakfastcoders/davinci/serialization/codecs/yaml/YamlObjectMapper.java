package io.breakfastcoders.davinci.serialization.codecs.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.Mapper;
import io.breakfastcoders.davinci.serialization.library.JacksonLibrary;

/**
 * A mapper is an abstraction that usually contains a {@link ObjectMapper object mapper}.
 * In addition, it typically adds a set of unique properties.
 *
 * <p>YAML based {@link Mapper mappers} include a single {@link PropertyNamingStrategy strategy}.
 * These {@link PropertyNamingStrategy strategies} are used for unique identification. </p>
 */
class YamlObjectMapper implements Mapper {
    private final ObjectMapper mapper;
    private final Strategy strategy;

    YamlObjectMapper(Strategy<JacksonLibrary> strategy) {
        this.strategy = strategy;
        this.mapper = new ObjectMapper(new YAMLFactory());
        this.mapper.setPropertyNamingStrategy(strategy.getMappingStrategy().getStrategy());
    }

    /**
     * Returns the constructed mapper from the static initialization block.
     *
     * <p> Properties:
     * 1. {@link PropertyNamingStrategy Strategy} used.
     * </p>
     *
     * @return Instance of ObjectMapper with the above properties
     */
    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

    @Override
    public Strategy getStrategy() {
        return strategy;
    }
}
