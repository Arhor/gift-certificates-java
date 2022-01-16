package com.epam.esm.gift.web.error;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class ApiError {

    private final String message;
    private final ErrorCode code;
    private final LocalDateTime timestamp;

    public ApiError(final String message) {
        this(
            message,
            ErrorCode.UNCATEGORIZED,
            LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS)
        );
    }

    public ApiError(final String message, final ErrorCode code) {
        this(
            message,
            code,
            LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS)
        );
    }

    public ApiError(final String message, final ErrorCode code, final LocalDateTime timestamp) {
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }

    public String message() {
        return message;
    }

    public ErrorCode code() {
        return code;
    }

    public LocalDateTime timestamp() {
        return timestamp;
    }
}
