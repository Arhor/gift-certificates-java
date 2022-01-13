package com.epam.esm.gift.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.gift.converter.CertificateEntityMapper;
import com.epam.esm.gift.converter.TagEntityMapper;
import com.epam.esm.gift.dto.CertificateDTO;
import com.epam.esm.gift.dto.TagDTO;
import com.epam.esm.gift.error.EntityNotFoundException;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.CertificateRepository;
import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.service.BaseService;

@Transactional
@Service
public class CertificateServiceImpl implements BaseService<CertificateDTO, Long> {

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
    public CertificateDTO findOne(final Long id) {
        return certificateRepository.findById(id)
            .map(certificateConverter::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .orElseThrow(() -> certificateIsNotFound(id));
    }

    @Override
    public List<CertificateDTO> findAll() {
        return certificateRepository.findAll()
            .stream()
            .map(certificateConverter::mapEntityToDto)
            .map(this::initializeCertificateTags)
            .toList();
    }

    @Override
    public CertificateDTO create(final CertificateDTO item) {
        var certificate = certificateConverter.mapDtoToEntity(item);
        var createdCertificate = certificateRepository.create(certificate.copy().build());
        var linkedTags = linkTagsToCertificate(createdCertificate.getId(), item.tags());
        return certificateConverter.mapEntityToDto(createdCertificate).copy().tags(linkedTags).build();
    }

    @Override
    public CertificateDTO update(final CertificateDTO item) {
        var certificate = certificateConverter.mapDtoToEntity(item);
        var updatedCertificate = certificateRepository.update(certificate);

        tagRepository.removeAllTagsFromCertificate(item.id());

        var linkedTags = linkTagsToCertificate(item.id(), item.tags());

        return certificateConverter.mapEntityToDto(updatedCertificate).copy().tags(linkedTags).build();
    }

    @Override
    public void deleteById(final Long id) {
        var certificate = certificateRepository.findById(id).orElseThrow(() -> certificateIsNotFound(id));
        certificateRepository.delete(certificate);
    }

    private CertificateDTO initializeCertificateTags(final CertificateDTO certificate) {
        var certificateTags = tagRepository.findTagsByCertificateId(certificate.id())
            .stream()
            .map(tagConverter::mapEntityToDto)
            .toList();
        return certificate.copy().tags(certificateTags).build();
    }

    private List<TagDTO> linkTagsToCertificate(final Long certificateId, final List<TagDTO> tags) {
        if ((certificateId == null) || (tags == null) || tags.isEmpty()) {
            return List.of();
        }

        var tagNames = tags.stream().map(TagDTO::name).filter(Objects::nonNull).toList();
        var existingTags = tagRepository.findTagByNames(tagNames);
        var existingTagNames = existingTags.stream().map(Tag::getName).toList();

        var createdTags = tags.stream()
            .filter(it -> !existingTagNames.contains(it.name()))
            .map(tagConverter::mapDtoToEntity)
            .map(tagRepository::create)
            .toList();

        var certificateTags = new ArrayList<Tag>(existingTags.size() + createdTags.size()) {{
            addAll(existingTags);
            addAll(createdTags);
        }};

        tagRepository.addTagsToCertificate(certificateId, certificateTags);

        return certificateTags.stream().map(tagConverter::mapEntityToDto).toList();
    }

    private EntityNotFoundException certificateIsNotFound(final Long id) {
        return new EntityNotFoundException("certificate", "id = " + id);
    }
}