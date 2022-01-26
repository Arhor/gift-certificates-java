package com.epam.esm.gift.localization.error;

public abstract class PropertyConditionException extends LocalizableException {

    private final String name;
    private final String condition;

    protected PropertyConditionException(final ErrorLabel label, final String name, final String condition) {
        super(label);
        this.name = name;
        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{name, condition};
    }
}