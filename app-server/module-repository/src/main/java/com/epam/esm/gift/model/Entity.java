package com.epam.esm.gift.model;

import java.io.Serializable;

public interface Entity<T extends Serializable> {

    T getId();

    void setId(T id);
}