package com.epam.esm.gift.error;

public final class EntityDuplicateException extends PropertyConditionException {

    public EntityDuplicateException(final String name, final String condition) {
        super(name, condition);
    }
}