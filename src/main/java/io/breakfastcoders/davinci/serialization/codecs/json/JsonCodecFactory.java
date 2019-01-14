package io.breakfastcoders.davinci.serialization.codecs.json;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.CodecFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.Contract;

/**
 * Factories are for controlling the internment pool for implementations of {@link JsonCodec codec}.
 * The factories have a two fold purpose:
 * <ul>
 *   <li>
 *     <p>
 *       constructing new instances of {@link JsonCodec codecs} in the absence of a usable instance
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *      controlling instances of {@link JsonCodec codecs} through object interning via a simple pool.
 *     </p>
 *   </li>
 * </ul>
 *
 * <p>This factory control the {@link JsonCodec codec} instances</p>
 */
@ThreadSafe
public class JsonCodecFactory implements CodecFactory<JsonCodec, Strategy> {
    private final Map<Strategy, JsonCodec> codecMap = new ConcurrentHashMap<>();

    private JsonCodecFactory() {
    }

    /**
     * This class follows the static singleton pattern.
     * As such it uses the Joshua Bloch, singleton access pattern.
     * The method retrieves the instance from the {@link JsonCodecFactoryHolder holder}
     * If no instance exists, it will use the private construct to construct it.
     * If it does, it will retrieve it.
     *
     * @return Instance of the {@link JsonCodecFactory factory}, guaranteed to be the only one.
     */
    @Contract(pure = true)
    public static synchronized JsonCodecFactory getInstance() {
        return JsonCodecFactoryHolder.instance;
    }

    /**
     * Method for controlling instances of {@link JsonCodec codecs}.
     * If a current {@link JsonCodec codec} for the given key does not exist
     * then it will proceed to construct it
     *
     * @return {@link JsonCodec Codec} instance, guaranteed to be the only one
     */
    @Override
    public JsonCodec getCodec(Strategy strategy) {
        JsonCodec codec = codecMap.get(strategy);
        if (codec == null) {
            return codecMap.computeIfAbsent(strategy, x -> new JsonCodec(strategy));
        }
        return codec;
    }

    /**
     * Clears the current {@link JsonCodec codecs} contained within the pool.
     * Mostly used for testing and validation of memory constraints
     */
    @Override
    public void clear() {
        codecMap.clear();
    }

    /**
     * Inner static class not to be referenced by outside consumers.
     * This is part of the static singleton.
     */
    private static class JsonCodecFactoryHolder {
        private static JsonCodecFactory instance = new JsonCodecFactory();
    }
}
