package com.epam.esm.gift.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.gift.dto.TagDTO;
import com.epam.esm.gift.error.EntityDuplicateException;
import com.epam.esm.gift.error.EntityNotFoundException;
import com.epam.esm.gift.mapper.EntityMapper;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.TagRepository;
import com.epam.esm.gift.service.BaseService;

@Service
public class TagServiceImpl implements BaseService<TagDTO, Long> {

    private final TagRepository tagRepository;
    private final EntityMapper<Tag, TagDTO> tagConverter;

    @Autowired
    public TagServiceImpl(final TagRepository tagRepository, final EntityMapper<Tag, TagDTO> tagConverter) {
        this.tagRepository = tagRepository;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDTO findOne(final Long id) {
        return tagRepository.findById(id).map(tagConverter::mapEntityToDto).orElseThrow(() -> tagIsNotFound(id));
    }

    @Override
    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream().map(tagConverter::mapEntityToDto).toList();
    }

    @Override
    public TagDTO create(final TagDTO item) {
        checkIfTagNameAlreadyExists(item.name());

        var newTag = tagConverter.mapDtoToEntity(item);
        var createdTag = tagRepository.create(newTag);

        return tagConverter.mapEntityToDto(createdTag);
    }

    @Override
    public TagDTO update(final TagDTO item) {
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
