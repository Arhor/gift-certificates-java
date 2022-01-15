package com.epam.esm.gift.web.error;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public record ApiError(String message, ErrorCode code, LocalDateTime timestamp) {

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
}
