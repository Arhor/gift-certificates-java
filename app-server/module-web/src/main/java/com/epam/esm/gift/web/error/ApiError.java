package com.epam.esm.gift.web.error;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "code",
    "traceId",
    "message",
    "details",
    "timestamp",
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiError implements Serializable {

    private final UUID traceId;
    private final String message;
    private final ErrorCode code;
    private final List<String> details;
    private final LocalDateTime timestamp;

    public ApiError(final UUID traceId, final String message) {
        this(traceId, message, ErrorCode.UNCATEGORIZED);
    }

    public ApiError(final UUID traceId, final String message, final ErrorCode code) {
        this(traceId, message, code, null);
    }

    public ApiError(final UUID traceId, final String message, final ErrorCode code, final List<String> details) {
        this(traceId, message, code, details, LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));
    }

    public ApiError(
        final UUID traceId,
        final String message,
        final ErrorCode code,
        final List<String> details,
        final LocalDateTime timestamp
    ) {
        this.traceId = traceId;
        this.message = message;
        this.code = code;
        this.details = details;
        this.timestamp = timestamp;
    }

    @JsonGetter
    public UUID getTraceId() {
        return traceId;
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

    @JsonGetter
    public List<String> getDetails() {
        return details;
    }
}
