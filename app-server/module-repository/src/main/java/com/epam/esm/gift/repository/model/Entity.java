package com.epam.esm.gift.repository.model;

import java.io.Serializable;

public interface Entity<T extends Serializable> {

    T getId();

    void setId(T id);
}
