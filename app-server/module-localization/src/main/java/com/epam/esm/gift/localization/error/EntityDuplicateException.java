package com.epam.esm.gift.localization.error;

import static com.epam.esm.gift.localization.error.ErrorLabel.ERROR_ENTITY_DUPLICATE;

public final class EntityDuplicateException extends PropertyConditionException {

    public EntityDuplicateException(final String name, final String condition) {
        super(ERROR_ENTITY_DUPLICATE, name, condition);
    }
}