package com.epam.esm.gift.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.gift.dto.TagDto;
import com.epam.esm.gift.localization.error.EntityDuplicateException;
import com.epam.esm.gift.localization.error.EntityNotFoundException;
import com.epam.esm.gift.mapper.EntityMapper;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.service.BaseService;

@Service
public class TagServiceImpl implements BaseService<TagDto, Long> {

    private static final String ERROR_TAG_IS_NULL = "Tag must not be null";
    private static final String ERROR_TAG_ID_IS_NULL = "Tag ID must not be null";

    private final TagRepository tagRepository;
    private final EntityMapper<Tag, TagDto> tagConverter;

    @Autowired
    public TagServiceImpl(final TagRepository tagRepository, final EntityMapper<Tag, TagDto> tagConverter) {
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDto findOne(final Long id) {
        Objects.requireNonNull(id, ERROR_TAG_ID_IS_NULL);

        return tagRepository.findById(id)
            .map(tagConverter::mapEntityToDto)
            .orElseThrow(() -> tagIsNotFound(id));
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll()
            .stream()
            .map(tagConverter::mapEntityToDto)
            .collect(toList());
    }

    @Override
    public TagDto create(final TagDto item) {
        Objects.requireNonNull(item, ERROR_TAG_ID_IS_NULL);

        checkIfTagNameAlreadyExists(item.name());

        var newTag = tagConverter.mapDtoToEntity(item);
        var createdTag = tagRepository.create(newTag);

        return tagConverter.mapEntityToDto(createdTag);
    }

    @Override
    public TagDto update(final TagDto item) {
        Objects.requireNonNull(item, ERROR_TAG_ID_IS_NULL);

        checkIfTagNameAlreadyExists(item.name());

        var tag = tagConverter.mapDtoToEntity(item);
        var updatedTag = tagRepository.update(tag);

        return tagConverter.mapEntityToDto(updatedTag);
    }

    @Override
    public void deleteById(final Long id) {
        var tag = tagRepository.findById(id).orElseThrow(() -> tagIsNotFound(id));
        tagRepository.delete(tag);
    }

    private void checkIfTagNameAlreadyExists(final String name) {
        tagRepository.findTagByName(name).ifPresent(it -> {
            throw tagNameIsAlreadyExists(it.getName());
        });
    }

    private EntityNotFoundException tagIsNotFound(final Long id) {
        return new EntityNotFoundException("tag", "id = " + id);
    }

    private EntityDuplicateException tagNameIsAlreadyExists(final String name) {
        return new EntityDuplicateException("tag", "name = " + name);
    }
}
