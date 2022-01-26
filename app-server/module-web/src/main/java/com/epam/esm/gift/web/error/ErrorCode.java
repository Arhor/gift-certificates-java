package com.epam.esm.gift.web.error;

import static com.epam.esm.gift.web.error.ErrorCode.Type.DAT;
import static com.epam.esm.gift.web.error.ErrorCode.Type.GEN;
import static com.epam.esm.gift.web.error.ErrorCode.Type.SEC;
import static com.epam.esm.gift.web.error.ErrorCode.Type.SRV;
import static com.epam.esm.gift.web.error.ErrorCode.Type.VAL;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode {
    // @formatter:off
    UNCATEGORIZED            (GEN, 0),

    VALIDATION_FAILED        (VAL, 0),

    SECURITY_VIOLATION       (SEC, 0),

    DATA_ACCESS_ERROR        (DAT, 0),
    NOT_FOUND                (DAT, 1),
    DUPLICATE                (DAT, 2),

    HANDLER_NOT_FOUND        (SRV, 0),
    METHOD_ARG_TYPE_MISMATCH (SRV, 1)
    // @formatter:on
    ;

    private final Type type;
    private final int numericValue;

    ErrorCode(final Type type, final int numericValue) {
        this.type = type;
        this.numericValue = numericValue;
    }

    public Type getType() {
        return type;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public enum Type {
        // @formatter:off
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        SRV("SERVER"),
        // @formatter:on
        ;

        private final String description;

        Type(final String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
