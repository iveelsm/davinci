package io.breakfastcoders.davinci.utils.annotations;

import io.breakfastcoders.davinci.annotations.Codec;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;

@Codec(strategy = SnakeCaseStrategy.class)
public class SimpleTestModel {
    public String stringValue;
    public Integer integerValue;
    public Double doubleValue;
}
