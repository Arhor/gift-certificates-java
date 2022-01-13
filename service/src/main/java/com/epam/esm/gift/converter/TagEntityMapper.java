package com.epam.esm.gift.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.epam.esm.gift.config.MapStructConfig;
import com.epam.esm.gift.dto.TagDTO;
import com.epam.esm.gift.model.Tag;

@Mapper(config = MapStructConfig.class)
public interface TagEntityMapper extends EntityMapper<Tag, TagDTO> {

    @Override
    Tag mapDtoToEntity(TagDTO item);

    @Override
    @InheritInverseConfiguration
    TagDTO mapEntityToDto(Tag item);
}
