package com.epam.esm.gift.web.error;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "code",
    "message",
    "timestamp",
})
public final class ApiError implements Serializable {

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

    @JsonGetter
    public String getMessage() {
        return message;
    }

    @JsonGetter
    public ErrorCode getCode() {
        return code;
    }

    @JsonGetter
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
