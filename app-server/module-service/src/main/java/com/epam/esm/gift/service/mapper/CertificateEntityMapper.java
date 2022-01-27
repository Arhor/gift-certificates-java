package com.epam.esm.gift.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.epam.esm.gift.repository.model.Certificate;
import com.epam.esm.gift.service.config.MapStructConfig;
import com.epam.esm.gift.service.dto.CertificateDto;

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
