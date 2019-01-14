package io.breakfastcoders.davinci.serialization.codecs;

import io.breakfastcoders.davinci.error.ParsingException;

/**
 * A codec is a means of transforming data from one representation to another.
 * This is intended to be done a variety of ways and will likely vary even throughout a single project.
 * The Codec is a means of abstraction all the serialization techniques.
 * This is typically used in formats of External data to Internal Object Transforms.
 * All Codecs should maintain the following methods:
 * <ul>
 *   <li>
 *     <p>
 *       decode, which takes external representations and converts them to internal representations
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       encode, which takes internal representations and converts them to external representations
 *     </p>
 *   </li>
 * </ul>
 *
 * @param <E> Considered the external representation. This parameter defines the input needed.
 *            This allows for flexibility of other input methods as the Codec system grows.
 *            This could be Strings, URIs, Files or other implementations.
 */
public interface Codec<E> {
    /**
     * Decode takes a representation and attempts to construct an object.
     * It can take files, strings, and other representation to attempt to decode.
     * All the possibilities are specified by the type param E.
     * There is only possibility per implementation of the class.
     *
     * @param <T>            Type of class were are expecting to construct from the input
     * @param representation Representation of the class in an external format defined by {@link Codec codec} type
     * @param clazz          Reference to class type to instantiate with data
     *                       Something like <code>Test.class</code>
     * @return An instance of the class from the representation
     * @throws ParsingException which represents a failure in the processing
     */
    <T> T decode(E representation, Class<T> clazz) throws ParsingException;

    /**
     * Encode takes a POJO and converts it into a external representation.
     * It can take classes and convert them into files, string or other representations.
     * All the possibilities are specified by the type param E.
     * There is only possibility per implementation of the class.
     *
     * @param <T>    Type of class we are expecting as input
     * @param object Class to write out into external representation
     * @return External representation, specified by {@link Codec codec} type of the passed in object for the given type
     * @throws ParsingException which represents a failure in the processing
     */
    <T> E encode(T object) throws ParsingException;
}
