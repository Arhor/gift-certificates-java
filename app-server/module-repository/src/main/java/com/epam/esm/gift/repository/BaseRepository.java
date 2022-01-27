package com.epam.esm.gift.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.epam.esm.gift.repository.model.Entity;

public interface BaseRepository<T extends Entity<K>, K extends Serializable> {

    T create(T entity);

    T update(T entity);

    List<T> findAll();

    Optional<T> findById(K id);

    void delete(T entity);

    void deleteById(K id);
}
