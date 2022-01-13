package com.epam.esm.gift.converter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.epam.esm.gift.config.MapStructConfig;
import com.epam.esm.gift.dto.CertificateDTO;
import com.epam.esm.gift.model.Certificate;

@Mapper(
    config = MapStructConfig.class,
    uses = {
        TagEntityMapper.class,
    }
)
public interface CertificateEntityMapper extends EntityMapper<Certificate, CertificateDTO> {

    @Override
    Certificate mapDtoToEntity(CertificateDTO item);

    @Override
    @InheritInverseConfiguration
    CertificateDTO mapEntityToDto(Certificate item);
}
