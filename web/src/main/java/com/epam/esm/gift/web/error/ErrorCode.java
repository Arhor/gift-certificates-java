package com.epam.esm.gift.web.error;

import static com.epam.esm.gift.web.error.ErrorCode.Type.DAT;
import static com.epam.esm.gift.web.error.ErrorCode.Type.GEN;
import static com.epam.esm.gift.web.error.ErrorCode.Type.SEC;
import static com.epam.esm.gift.web.error.ErrorCode.Type.VAL;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ErrorCodeSerializer.class)
public enum ErrorCode {
    // @formatter:off

    UNCATEGORIZED      (GEN, 0),

    VALIDATION_FAILED  (VAL, 0),

    SECURITY_VIOLATION (SEC, 0),

    DATA_ACCESS_ERROR  (DAT, 0),
    NOT_FOUND          (DAT, 1),
    DUPLICATE          (DAT, 2),

    // @formatter:on
    ;

    public final Type type;
    public final int code;

    ErrorCode(Type type, int code) {
        this.type = type;
        this.code = code;
    }

    public enum Type {
        GEN("GENERAL"),
        SEC("SECURITY"),
        VAL("VALIDATION"),
        DAT("DATA"),
        ;

        public final String description;

        Type(String description) {
            this.description = description;
        }
    }
}