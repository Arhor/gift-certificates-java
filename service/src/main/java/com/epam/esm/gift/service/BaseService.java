package com.epam.esm.gift.service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<D, K extends Serializable> {

    D findOne(K id);

    List<D> findAll();

    D create(D item);

    D update(D item);

    void deleteById(K id);
}