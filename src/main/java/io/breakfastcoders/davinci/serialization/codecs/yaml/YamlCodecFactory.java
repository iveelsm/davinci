package io.breakfastcoders.davinci.serialization.codecs.yaml;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.codecs.CodecFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.Contract;

/**
 * Factories are for controlling the internment pool for implementations of {@link YamlCodec codec}.
 * The factories have a two fold purpose:
 * <ul>
 *   <li>
 *     <p>
 *       constructing new instances of {@link YamlCodec codecs} in the absence of a usable instance
 *     </p>
 *   </li>
 *   <li>
 *     <p>
 *      controlling instances of {@link YamlCodec codecs} through object interning via a simple pool.
 *     </p>
 *   </li>
 * </ul>
 * Implementations are expected to be threadsafe.
 *
 * <p>
 * This factory control the {@link YamlCodec codec} instances
 * </p>
 */
@ThreadSafe
public class YamlCodecFactory implements CodecFactory<YamlCodec, Strategy> {
    private final Map<Strategy, YamlCodec> codecMap = new ConcurrentHashMap<>();

    private YamlCodecFactory() {
    }

    /**
     * This class follows the static singleton pattern.
     * As such it uses the Joshua Bloch, singleton access pattern.
     * The method retrieves the instance from the {@link YamlCodecFactoryHolder holder}
     * If no instance exists, it will use the private construct to construct it.
     * If it does, it will retrieve it.
     *
     * @return Instance of the {@link YamlCodecFactory factory}, guaranteed to be the only one.
     */
    @Contract(pure = true)
    public static synchronized YamlCodecFactory getInstance() {
        return YamlCodecFactoryHolder.instance;
    }

    /**
     * Method for retrieving instances of {@link YamlCodec codecs}.
     * If a current {@link YamlCodec codec} for the given key does not exist
     * then it will proceed to construct it
     *
     * @return {@link YamlCodec Codec} instance, guaranteed to be the only one
     */
    @Override
    public YamlCodec getCodec(Strategy strategy) {
        YamlCodec result = codecMap.get(strategy);
        if (result == null) {
            return codecMap.computeIfAbsent(strategy, x -> new YamlCodec(strategy));
        }
        return result;
    }

    /**
     * Clears all the current {@link YamlCodec codecs} contained within the pool.
     * Mostly used for testing and validation of memory constraints.
     */
    @Override
    public void clear() {
        codecMap.clear();
    }

    /**
     * Inner static class not to be referenced by outside consumers.
     * This is part of the static singleton.
     */
    private static class YamlCodecFactoryHolder {
        private static YamlCodecFactory instance = new YamlCodecFactory();
    }
}
