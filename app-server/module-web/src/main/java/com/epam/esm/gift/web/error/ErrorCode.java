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

    public final Type type;
    public final int code;

    ErrorCode(final Type type, final int code) {
        this.type = type;
        this.code = code;
    }

    public enum Type {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        SRV("SERVER"),

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