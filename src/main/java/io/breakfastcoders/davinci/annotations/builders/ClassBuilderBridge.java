package io.breakfastcoders.davinci.annotations.builders;

import io.breakfastcoders.davinci.annotations.SourceType;
import io.breakfastcoders.davinci.error.UnsupportedTypeException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class ClassBuilderBridge {
  /**
   * Gets a instance of a builder for use by the processors.
   * These are stored in static memory for minimizing compile time.
   *
   * @param type Source type for the processing
   * @return Instance of a builder for building classes
   */
    @NotNull
    @Contract("_ -> new")
    public static ClassBuilder getBuilder(@NotNull SourceType type) throws UnsupportedTypeException {
      return Builders.getBuilderForSourceType(type);
    }
}
