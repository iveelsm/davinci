package io.breakfastcoders.davinci.serialization.codecs.yaml;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.breakfastcoders.davinci.error.ExceptionStates;
import io.breakfastcoders.davinci.error.ParsingException;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.Codec;
import io.breakfastcoders.davinci.serialization.codecs.Mapper;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * An implementation of a {@link Codec codec} designed to...
 * 1. Parse URIs into YAML
 * 2. Parse Java Objects into YAML strings (less useful)
 */
public class YamlCodec implements Codec<String> {
    private final YamlObjectMapper mapper;

    YamlCodec(Strategy strategy) {
        this.mapper = new YamlObjectMapper(strategy);
    }

    /**
     * Constructs an object from a file URI in String representation.
     *
     * @param <T>   Type of class were are expecting to serialize into.
     * @param uri   String reference to file URI
     * @param clazz Reference to class type to instantiate with data.
     *              Used by Jackson to construct via reflection.
     * @return Instance of class determine from reading the file at the URI
     */
    @Override
    public <T> T decode(String uri, Class<T> clazz) throws ParsingException {
        try {
            return mapper.getMapper().readValue(Paths.get(uri).toFile(), clazz);
        } catch (JsonParseException e) {
            throw new ParsingException(ExceptionStates.MALFORMED_CONTENT, e.getCause());
        } catch (IOException e) {
            throw new ParsingException(ExceptionStates.INTERRUPTED_OPERATION, e.getCause());
        } catch (NullPointerException e) {
            throw new ParsingException(ExceptionStates.NULL_DATA, e.getCause());
        }
    }

    /**
     * Writes out an Object to a YAML-like String.
     *
     * @param <T>    Type of Object
     * @param object Object to write out into String format
     * @return String representation of the Object of type T
     */
    @Override
    public <T> String encode(T object) throws ParsingException {
        try {
            return mapper.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ParsingException(ExceptionStates.MALFORMED_CONTENT, e.getCause());
        } catch (NullPointerException e) {
            throw new ParsingException(ExceptionStates.NULL_DATA, e.getCause());
        }
    }

    /**
     * Gets the specific mapper implementation for this class.
     * Used mostly in testing.
     *
     * @return {@link Mapper mapper} being used for the parsing
     */
    Mapper getCodec() {
        return this.mapper;
    }
}
