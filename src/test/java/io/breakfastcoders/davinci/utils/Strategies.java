package io.breakfastcoders.davinci.utils;

import io.breakfastcoders.davinci.serialization.Strategy;
import io.breakfastcoders.davinci.serialization.strategy.CamelCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.KebabCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.PascalCaseStrategy;
import io.breakfastcoders.davinci.serialization.strategy.SnakeCaseStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Strategies {
    public static final Random r = new Random();

    public static List<Strategy> getStrategies() {
        List<Strategy> result = new ArrayList<>();
        result.add(new KebabCaseStrategy());
        result.add(new PascalCaseStrategy());
        result.add(new CamelCaseStrategy());
        result.add(new SnakeCaseStrategy());
        return result;
    }

    public static Strategy getRandomStrategy() {
        return getStrategies().get(r.nextInt(4));
    }
}
