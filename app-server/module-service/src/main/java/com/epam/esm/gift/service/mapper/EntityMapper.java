package com.epam.esm.gift.service.mapper;

public interface EntityMapper<A, B> {

    B mapEntityToDto(A item);

    A mapDtoToEntity(B item);
}
