package com.epam.esm.gift.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.esm.gift.dto.TagDto;
import com.epam.esm.gift.mapper.TagEntityMapper;
import com.epam.esm.gift.error.EntityNotFoundException;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagEntityMapper tagConverter;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void should_return_an_existing_tag_represented_as_DTO() {
        // given
        var id = 1L;
        var name = "test";
        var tag = Tag.builder().id(id).name(name).build();
        var tagDTO = new TagDto(id, name);

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        when(tagConverter.mapEntityToDto(tag)).thenReturn(tagDTO);

        // when
        var result = tagService.findOne(id);

        // then
        assertThat(result)
            .isNotNull()
            .isEqualTo(tagDTO);
    }

    @Test
    void should_throw_EntityNotFoundException() {
        // given
        var id = 1L;

        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        ThrowingCallable action = () -> tagService.findOne(id);

        // then
        assertThatThrownBy(action)
            .isNotNull()
            .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void should_return_existing_tags_represented_as_DTOs() {
        // given
        var id = 1L;
        var name = "test";
        var tag = Tag.builder().id(id).name(name).build();
        var tagDTO = new TagDto(id, name);

        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagConverter.mapEntityToDto(tag)).thenReturn(tagDTO);

        // when
        var result = tagService.findAll();

        // then
        assertThat(result)
            .isNotNull()
            .hasSize(1)
            .contains(tagDTO);
    }

    @Test
    void should_create_tag_entity() {
        // given
        var testId = 1L;
        var testName = "test";
        var tagDto = new TagDto(null, testName);
        var tagEntity = new Tag(null, testName);
        var expectedTagEntity = tagEntity.copy().id(testId).build();
        var expectedTagDto = tagDto.copy().id(testId).build();

        when(tagConverter.mapDtoToEntity(tagDto)).thenReturn(tagEntity);
        when(tagRepository.findTagByName(testName)).thenReturn(Optional.empty());
        when(tagRepository.create(tagEntity)).thenReturn(expectedTagEntity);
        when(tagConverter.mapEntityToDto(expectedTagEntity)).thenReturn(expectedTagDto);


        // when
        var createdTag = tagService.create(tagDto);

        // then
        assertThat(createdTag)
            .isNotNull()
            .isEqualTo(expectedTagDto);
    }
}