package io.breakfastcoders.davinci.serialization.codecs.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.breakfastcoders.davinci.error.ExceptionStates;
import io.breakfastcoders.davinci.error.ParsingException;
import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.Codec;
import io.breakfastcoders.davinci.serialization.codecs.Mapper;
import java.io.IOException;

/**
 * A codec is a means of transforming data from one representation to another.
 * This is intended to be done a variety of ways and will likely vary even throughout a single project.
 * The Codec is a means of abstraction all the serialization techniques.
 * This is typically used in formats of External data to Internal Object Transforms.
 * This codec does the following:
 * <ul>
 *   <li>
 *     <p>
 *      decode, which takes a JSON-like string and converts them into an object
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       encode, which takes an object and converts them to JSON-like string
 *     </p>
 *   </li>
 * </ul>
 */
public class JsonCodec implements Codec<String> {
    private final JsonObjectMapper codec;

    JsonCodec(Strategy strategy) {
        this.codec = new JsonObjectMapper(strategy);
    }

    /**
     * Decode takes a representation and attempts to construct an object.
     * This implementation uses JSON-like strings as our external representation.
     *
     * @param <T>   Type of class were are expecting to construct from the input
     * @param json  JSON-like string to parse into the object
     * @param clazz Reference to class type to instantiate with data
     *              Something like <code>Test.class</code>
     * @return An instance of the class from the representation
     * @throws ParsingException which represents a failure in the processing
     */
    @Override
    public <T> T decode(String json, Class<T> clazz) throws ParsingException {
        try {
            return codec.getMapper().readValue(json, clazz);
        } catch (JsonMappingException e) {
            throw new ParsingException(ExceptionStates.MALFORMED_CONTENT, e.getCause());
        } catch (IOException e) {
            throw new ParsingException(ExceptionStates.INTERRUPTED_OPERATION, e.getCause());
        } catch (NullPointerException e) {
            throw new ParsingException(ExceptionStates.NULL_DATA, e.getCause());
        }
    }

    /**
     * Encode takes a POJO and converts it into a JSON-like string.
     *
     * @param <T>    Type of class we are expecting as input
     * @param object Class to write out into external representation
     * @return JSON-like string, specified by {@link Codec codec} type of the passed in object for the given type
     * @throws ParsingException which represents a failure in the processing
     */
    @Override
    public <T> String encode(T object) throws ParsingException {
        try {
            return codec.getMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ParsingException(ExceptionStates.MALFORMED_CONTENT, e.getCause());
        } catch (NullPointerException e) {
            throw new ParsingException(ExceptionStates.NULL_DATA, e.getCause());
        }
    }

    /**
     * Gets the {@link Mapper mapper} used to construct this implementation.
     *
     * @return Mapper instance used for the construction
     */
    Mapper getCodec() {
        return this.codec;
    }
}
