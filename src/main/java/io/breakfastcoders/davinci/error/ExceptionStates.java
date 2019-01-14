package io.breakfastcoders.davinci.error;

import org.jetbrains.annotations.Contract;

/**
 * An enumerated set of states to report to downstream consumers on errors.
 * This are meant to obfuscate and collapse the possible JSON Errors while still being actionable themselves.
 */
public enum ExceptionStates {
    MALFORMED_CONTENT("The content passed into the method was malformed"),
    INTERRUPTED_OPERATION("The operation was interrupted during processing"),
    NULL_DATA("The data passed into the method was null");

    private final String message;

    ExceptionStates(String message) {
        this.message = message;
    }

    /**
     * Gets the intended message to be propagated into the reporting mechanism.
     * In {@link Exception exception} subclasses, this would be referenced via {@code getMessage()} calls
     *
     * @return String for the message to pass to downstream consumers
     */
    @Contract(pure = true)
    public String getMessage() {
        return this.message;
    }
}
