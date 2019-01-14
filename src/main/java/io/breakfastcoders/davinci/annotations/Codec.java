package io.breakfastcoders.davinci.annotations;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A JsonCodec is a class that should be serializing via JSON-like Strings.
 * These classes are simple data types classes that are intended to be extended to have simple serialization techniques applied.
 * Long term, this should be idempotent and stackable for a variety of different configurations.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Codec {

    /**
     * A strategy to use for the serialization.
     * Should indicate all the necessary properties for the underlying libraries to do the serialization.
     *
     * @return Class reference for an extension of the {@link Strategy Strategy} interface
     */
    Class<? extends Strategy<?>> strategy() default SnakeCaseStrategy.class;


  /**
   * Indicates the expected source type for the content.
   * Anything supported in the {@link SourceType source type} is allowed.
   *
   * @return Source type for processing
   */
  SourceType type() default SourceType.JSON;
}
