package com.epam.esm.gift.error;

public final class EntityNotFoundException extends PropertyConditionException {

    public EntityNotFoundException(final String name, final String condition) {
        super(name, condition);
    }
}
