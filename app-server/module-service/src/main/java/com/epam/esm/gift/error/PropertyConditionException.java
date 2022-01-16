package com.epam.esm.gift.error;

public abstract sealed class PropertyConditionException extends ServiceLayerException
    permits EntityDuplicateException, EntityNotFoundException {

    private final String name;
    private final String condition;

    protected PropertyConditionException(final String name, final String condition) {
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }
}