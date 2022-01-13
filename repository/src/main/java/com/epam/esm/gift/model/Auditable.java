package com.epam.esm.gift.model;

import java.time.temporal.TemporalAccessor;

public interface Auditable<T extends TemporalAccessor> {

    T getDateTimeCreated();

    void setDateTimeCreated(T dateTimeCreated);

    T getDateTimeUpdated();

    void setDateTimeUpdated(T dateTimeUpdated);
}