package com.epam.esm.gift.localization.error;

import com.epam.esm.gift.localization.Label;

public enum ErrorLabel implements Label {

    ERROR_UNCATEGORIZED    ("error.uncategorized"),
    ERROR_ENTITY_NOT_FOUND ("error.entity.not.found"),
    ERROR_ENTITY_DUPLICATE ("error.entity.duplicate"),

    ;

    private final String code;

    ErrorLabel(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
