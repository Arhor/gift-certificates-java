package com.epam.esm.gift.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.epam.esm.gift.repository.model.Tag;
import com.epam.esm.gift.service.config.MapStructConfig;
import com.epam.esm.gift.service.dto.TagDto;

@Mapper(config = MapStructConfig.class)
public interface TagEntityMapper extends EntityMapper<Tag, TagDto> {

    @Override
    Tag mapDtoToEntity(TagDto item);

    @Override
    @InheritInverseConfiguration
    TagDto mapEntityToDto(Tag item);
}
