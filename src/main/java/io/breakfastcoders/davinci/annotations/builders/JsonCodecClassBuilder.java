package io.breakfastcoders.davinci.annotations.builders;

import com.google.errorprone.annotations.Immutable;
import io.breakfastcoders.davinci.annotations.AnnotationHelpers;
import io.breakfastcoders.davinci.serialization.Strategy;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.breakfastcoders.davinci.annotations.AnnotationHelpers.formatString;
import static io.breakfastcoders.davinci.annotations.processors.AnnotationProcessor.STRATEGY_FIELD;

/**
 * Class Builder representation for the {@link io.breakfastcoders.davinci.annotations.Codec codec}.
 * Constructs the generated file for classes annotated with that particular annotation.
 */
@Immutable
class JsonCodecClassBuilder implements ClassBuilder {

    JsonCodecClassBuilder() { }

    /**
     * Builds a string representing the class contents.
     * Generates any methods, imports, package declarations or class declarations necessary.
     *
     * @param className Class name to construct for
     * @param strategy Strategy to use in the generated methods
     * @return String representing class contents
     */
    @Override
    @Nullable
    @Contract(pure = true)
    public String build(String className, @NotNull Class<? extends Strategy<?>> strategy) {
        String importStrategyName = strategy.getCanonicalName();
        String strategyName = strategy.getSimpleName();
        String packageName = AnnotationHelpers.getPackage(className).toString();
        String relativeName = AnnotationHelpers.getRelativeName(className).toString();

        return String.format("%s%s%s%s}%n",
                buildClass(packageName, relativeName, importStrategyName),
                buildFields(strategyName),
                encodeMethod(relativeName),
                decodeMethod(relativeName));
    }

    @NotNull
    private String buildClass(String packageName, String className, String strategy) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatString(String.format("package %s;%n", packageName), 0));
        builder.append(formatString(String.format("import %s;", strategy), 0));
        builder.append(formatString(String.format("import io.breakfastcoders.davinci.error.ParsingException;"), 0));
        builder.append(formatString(String.format("import io.breakfastcoders.davinci.serialization.codecs.json.JsonCodecFactory;%n"), 0));
        builder.append(formatString(String.format("public class %sBuilder {", className), 0));
        return builder.toString();
    }

    @NotNull
    private String buildFields(String strategy) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatString(String.format(
                "private static final %s %s = new %s();%n",
                strategy, STRATEGY_FIELD, strategy), 1));
        return builder.toString();
    }

    @NotNull
    private String encodeMethod(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatString(String.format("public static String encode(%s value) throws ParsingException {", className), 1));
        builder.append(formatString(String.format("return JsonCodecFactory.getInstance().getCodec(%s).encode(value);", STRATEGY_FIELD), 2));
        builder.append(formatString("}\n", 1));
        return builder.toString();
    }

    @NotNull
    private String decodeMethod(String className) {
        StringBuilder builder = new StringBuilder();
        builder.append(formatString(String.format("public static %s decode(String json) throws ParsingException {", className), 1));
        builder.append(formatString(String.format("return JsonCodecFactory.getInstance().getCodec(%s).decode(json, %s.class);",
                STRATEGY_FIELD, className), 2));
        builder.append(formatString("}", 1));
        return builder.toString();
    }
}
