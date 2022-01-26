package com.epam.esm.gift.localization.error;

import static com.epam.esm.gift.localization.error.ErrorLabel.ERROR_ENTITY_NOT_FOUND;

public final class EntityNotFoundException extends PropertyConditionException {

    public EntityNotFoundException(final String name, final String condition) {
        super(ERROR_ENTITY_NOT_FOUND, name, condition);
    }
}
