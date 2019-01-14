package io.breakfastcoders.davinci.annotations.builders;

import io.breakfastcoders.davinci.annotations.SourceType;
import io.breakfastcoders.davinci.error.UnsupportedTypeException;

public enum Builders {
  JSON(new JsonCodecClassBuilder());

  private final ClassBuilder builder;

  Builders(ClassBuilder builder) {
    this.builder = builder;
  }

  static ClassBuilder getBuilderForSourceType(SourceType type) throws UnsupportedTypeException {
    switch (type) {
      case JSON:
        return JSON.builder;
      case YAML:
        throw new UnsupportedTypeException();
      default:
        throw new UnsupportedTypeException();
    }
  }
}
