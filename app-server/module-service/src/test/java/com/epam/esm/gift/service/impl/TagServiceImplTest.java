package com.epam.esm.gift.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.esm.gift.mapper.TagEntityMapper;
import com.epam.esm.gift.dto.TagDTO;
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
        var tagDTO = new TagDTO(id, name);

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
}