package io.breakfastcoders.davinci.serialization.codecs;

import io.breakfastcoders.davinci.serialization.Strategy;

/**
 * Factories are for controlling the internment pool for implementations of {@link Codec codec}.
 * The factories have a two fold purpose:
 * <ul>
 *   <li>
 *     <p>
 *       constructing new instances of {@link Codec codecs} in the absence of a usable instance
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *       controlling instances of {@link Codec codecs} through object interning via a simple pool.
 *     </p>
 *   </li>
 * </ul>
 * Implementations are expected to be threadsafe.
 *
 * @param <T> A downstream implementation of {@link Codec codec}.
 *            Since {@link Codec codecs} are dependent on {@link Strategy strategies}
 *            there can be a number of implementations per type.
 * @param <E> This represents a key for the internment pool to return or construct objects with.
 */
public interface CodecFactory<T extends Codec, E> {
    /**
     * Method for retrieving instances of {@link Codec codecs}.
     * If a current {@link Codec codec} for the given key does not exist
     * then it will proceed to construct it
     *
     * @return {@link Codec Codec} instance, guaranteed to be the only one
     */
    T getCodec(E key);

    /**
     * Clears all the current {@link Codec codecs} contained within the pool.
     * Mostly used for testing and validation of memory constraints.
     */
    void clear();
}
