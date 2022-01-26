package com.epam.esm.gift.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.gift.dto.CertificateDto;
import com.epam.esm.gift.dto.TagDto;
import com.epam.esm.gift.localization.error.EntityNotFoundException;
import com.epam.esm.gift.mapper.CertificateEntityMapper;
import com.epam.esm.gift.mapper.TagEntityMapper;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.CertificateRepository;
import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.service.BaseService;

@Service
public class CertificateServiceImpl implements BaseService<CertificateDto, Long> {

    private static final String ERROR_CERTIFICATE_IS_NULL = "Certificate must not be null";
    private static final String ERROR_CERTIFICATE_ID_IS_NULL = "Certificate ID must not be null";
    private static final String ERROR_TAG_LIST_IS_NULL = "Tags list must not be null";

    private final CertificateRepository certificateRepository;
    private final CertificateEntityMapper certificateMapper;
    private final TagRepository tagRepository;
    private final TagEntityMapper tagMapper;

    public CertificateServiceImpl(
        final CertificateRepository certificateRepository,
        final CertificateEntityMapper certificateMapper,
        final TagRepository tagRepository,
        final TagEntityMapper tagMapper
    ) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CertificateDto findOne(final Long id) {
        Objects.requireNonNull(id, ERROR_CERTIFICATE_ID_IS_NULL);

        return certificateRepository.findById(id)
            .map(certificateMapper::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .orElseThrow(() -> certificateIsNotFound(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateDto> findAll() {
        return certificateRepository.findAll()
            .stream()
            .map(certificateMapper::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .collect(toList());
    }

    @Override
    @Transactional
    public CertificateDto create(final CertificateDto item) {
        Objects.requireNonNull(item, ERROR_CERTIFICATE_IS_NULL);

        var certificate = certificateMapper.mapDtoToEntity(item);
        var createdCertificate = certificateRepository.create(certificate.copy().build());

        var linkedTags = linkTagsToCertificate(createdCertificate.getId(), item.tags());

        return certificateMapper.mapEntityToDto(createdCertificate).copy().tags(linkedTags).build();
    }

    @Override
    @Transactional
    public CertificateDto update(final CertificateDto item) {
        Objects.requireNonNull(item, ERROR_CERTIFICATE_IS_NULL);

        var certificate = certificateMapper.mapDtoToEntity(item);
        var updatedCertificate = certificateRepository.update(certificate);

        tagRepository.removeAllTagsFromCertificate(item.id());

        var linkedTags = linkTagsToCertificate(item.id(), item.tags());

        return certificateMapper.mapEntityToDto(updatedCertificate).copy().tags(linkedTags).build();
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        Objects.requireNonNull(id, ERROR_CERTIFICATE_ID_IS_NULL);
        var certificate = certificateRepository.findById(id).orElseThrow(() -> certificateIsNotFound(id));
        certificateRepository.delete(certificate);
    }

    private CertificateDto initializeCertificateTags(final CertificateDto certificate) {
        Objects.requireNonNull(certificate, ERROR_CERTIFICATE_IS_NULL);

        var certificateTags = tagRepository.findTagsByCertificateId(certificate.id())
            .stream()
            .map(tagMapper::mapEntityToDto)
            .collect(toList());

        return certificate.copy().tags(certificateTags).build();
    }

    private List<TagDto> linkTagsToCertificate(final Long certificateId, final List<TagDto> tags) {
        Objects.requireNonNull(certificateId, ERROR_CERTIFICATE_ID_IS_NULL);
        Objects.requireNonNull(tags, ERROR_TAG_LIST_IS_NULL);

        if (tags.isEmpty()) {
            return Collections.emptyList();
        }

        var tagNames = tags.stream().map(TagDto::name).filter(Objects::nonNull).collect(toList());
        var existingTags = tagRepository.findTagByNames(tagNames);
        var existingTagNames = existingTags.stream().map(Tag::getName).collect(toList());

        var createdTags = tags.stream()
            .filter(it -> !existingTagNames.contains(it.name()))
            .map(tagMapper::mapDtoToEntity)
            .map(tagRepository::create)
            .collect(toList());

        var certificateTags = ListUtils.union(existingTags, createdTags);

        tagRepository.addTagsToCertificate(certificateId, certificateTags);

        return certificateTags.stream().map(tagMapper::mapEntityToDto).collect(toList());
    }

    private EntityNotFoundException certificateIsNotFound(final Long id) {
        return new EntityNotFoundException("certificate", "id = " + id);
    }
}