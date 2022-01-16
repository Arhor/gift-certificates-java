package com.epam.esm.gift.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.epam.esm.gift.config.MapStructConfig;
import com.epam.esm.gift.dto.TagDto;
import com.epam.esm.gift.model.Tag;

@Mapper(config = MapStructConfig.class)
public interface TagEntityMapper extends EntityMapper<Tag, TagDto> {

    @Override
    Tag mapDtoToEntity(TagDto item);

    @Override
    @InheritInverseConfiguration
    TagDto mapEntityToDto(Tag item);
}
