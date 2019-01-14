package io.breakfastcoders.davinci.error;

import java.io.IOException;
import org.jetbrains.annotations.NotNull;

/**
 * An extension of {@link IOException IOexception} for specific causes.
 * Currently just a pure extension.
 * Uses {@link ExceptionStates states} to define possible messages to report to the consumers
 */
public class ParsingException extends IOException {
    public ParsingException(@NotNull ExceptionStates states, Throwable cause) {
        super(states.getMessage(), cause);
    }
}
