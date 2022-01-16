package com.epam.esm.gift.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.gift.dto.CertificateDTO;
import com.epam.esm.gift.dto.TagDTO;
import com.epam.esm.gift.error.EntityNotFoundException;
import com.epam.esm.gift.mapper.CertificateEntityMapper;
import com.epam.esm.gift.mapper.TagEntityMapper;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.CertificateRepository;
import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.service.BaseService;

@Service
public class CertificateServiceImpl implements BaseService<CertificateDTO, Long> {

    private static final String ERROR_CERTIFICATE_IS_NULL = "Certificate must not be null";
    private static final String ERROR_CERTIFICATE_ID_IS_NULL = "Certificate ID must not be null";
    private static final String ERROR_TAG_LIST_IS_NULL = "Tags list must not be null";

    private final CertificateRepository certificateRepository;
    private final CertificateEntityMapper certificateConverter;
    private final TagRepository tagRepository;
    private final TagEntityMapper tagConverter;

    public CertificateServiceImpl(
        final CertificateRepository certificateRepository,
        final CertificateEntityMapper certificateConverter,
        final TagRepository tagRepository,
        final TagEntityMapper tagConverter
    ) {
        this.certificateRepository = certificateRepository;
        this.certificateConverter = certificateConverter;
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDTO findOne(final Long id) {
        Objects.requireNonNull(id, ERROR_CERTIFICATE_ID_IS_NULL);

        return certificateRepository.findById(id)
            .map(certificateConverter::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .orElseThrow(() -> certificateIsNotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDTO> findAll() {
        return certificateRepository.findAll()
            .stream()
            .map(certificateConverter::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .toList();
    }

    @Override
    @Transactional
    public CertificateDTO create(final CertificateDTO item) {
        Objects.requireNonNull(item, ERROR_CERTIFICATE_IS_NULL);

        var certificate = certificateConverter.mapDtoToEntity(item);
        var createdCertificate = certificateRepository.create(certificate.copy().build());

        var linkedTags = linkTagsToCertificate(createdCertificate.getId(), item.tags());

        return certificateConverter.mapEntityToDto(createdCertificate).copy().tags(linkedTags).build();
    }

    @Override
    @Transactional
    public CertificateDTO update(final CertificateDTO item) {
        Objects.requireNonNull(item, ERROR_CERTIFICATE_IS_NULL);

        var certificate = certificateConverter.mapDtoToEntity(item);
        var updatedCertificate = certificateRepository.update(certificate);

        tagRepository.removeAllTagsFromCertificate(item.id());

        var linkedTags = linkTagsToCertificate(item.id(), item.tags());

        return certificateConverter.mapEntityToDto(updatedCertificate).copy().tags(linkedTags).build();
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        Objects.requireNonNull(id, ERROR_CERTIFICATE_ID_IS_NULL);
        var certificate = certificateRepository.findById(id).orElseThrow(() -> certificateIsNotFound(id));
        certificateRepository.delete(certificate);
    }

    private CertificateDTO initializeCertificateTags(final CertificateDTO certificate) {
        Objects.requireNonNull(certificate, ERROR_CERTIFICATE_IS_NULL);

        var certificateTags = tagRepository.findTagsByCertificateId(certificate.id())
            .stream()
            .map(tagConverter::mapEntityToDto)
            .toList();

        return certificate.copy().tags(certificateTags).build();
    }

    private List<TagDTO> linkTagsToCertificate(final Long certificateId, final List<TagDTO> tags) {
        Objects.requireNonNull(certificateId, ERROR_CERTIFICATE_ID_IS_NULL);
        Objects.requireNonNull(tags, ERROR_TAG_LIST_IS_NULL);

        if (tags.isEmpty()) {
            return Collections.emptyList();
        }

        var tagNames = tags.stream().map(TagDTO::name).filter(Objects::nonNull).toList();
        var existingTags = tagRepository.findTagByNames(tagNames);
        var existingTagNames = existingTags.stream().map(Tag::getName).toList();

        var createdTags = tags.stream()
            .filter(it -> !existingTagNames.contains(it.name()))
            .map(tagConverter::mapDtoToEntity)
            .map(tagRepository::create)
            .toList();

        var certificateTags = ListUtils.union(existingTags, createdTags);

        tagRepository.addTagsToCertificate(certificateId, certificateTags);

        return certificateTags.stream().map(tagConverter::mapEntityToDto).toList();
    }

    private EntityNotFoundException certificateIsNotFound(final Long id) {
        return new EntityNotFoundException("certificate", "id = " + id);
    }
}