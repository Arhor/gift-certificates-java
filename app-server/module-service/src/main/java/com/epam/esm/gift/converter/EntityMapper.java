package com.epam.esm.gift.converter;

public interface EntityMapper<A, B> {

    B mapEntityToDto(A item);

    A mapDtoToEntity(B item);
}