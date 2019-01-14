package io.breakfastcoders.davinci.serialization.library;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JacksonLibrary implements Library {
  private final PropertyNamingStrategy strategy;

  public JacksonLibrary(PropertyNamingStrategy strategy) {
    this.strategy = strategy;
  }

  public PropertyNamingStrategy getStrategy() {
    return this.strategy;
  }
}
