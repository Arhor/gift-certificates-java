package com.epam.esm.gift.localization.error;

public abstract class LocalizableException extends RuntimeException {

    private final ErrorLabel label;

    protected LocalizableException(final ErrorLabel label) {
        this.label = label;
    }

    public ErrorLabel getLabel() {
        return label;
    }
}
