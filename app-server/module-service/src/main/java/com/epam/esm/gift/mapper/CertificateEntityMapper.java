package com.epam.esm.gift.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.epam.esm.gift.config.MapStructConfig;
import com.epam.esm.gift.dto.CertificateDto;
import com.epam.esm.gift.model.Certificate;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        TagEntityMapper.class,
    }
)
public interface CertificateEntityMapper extends EntityMapper<Certificate, CertificateDto> {

    @Override
    Certificate mapDtoToEntity(CertificateDto item);

    @Override
    @InheritInverseConfiguration
    @Mapping(target = "tags", ignore = true)
    CertificateDto mapEntityToDto(Certificate item);
}
