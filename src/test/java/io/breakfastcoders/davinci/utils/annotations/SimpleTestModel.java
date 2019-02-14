package io.breakfastcoders.davinci.utils.annotations;

import io.breakfastcoders.davinci.annotations.Codec;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;

@Codec(strategy = SnakeCaseStrategy.class)
public class SimpleTestModel {
    private String stringValue;
    private Integer integerValue;
    private Double doubleValue;

    public SimpleTestModel(String stringValue, Integer integerValue, Double doubleValue) {
        this.stringValue = stringValue;
        this.integerValue = integerValue;
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }
}
