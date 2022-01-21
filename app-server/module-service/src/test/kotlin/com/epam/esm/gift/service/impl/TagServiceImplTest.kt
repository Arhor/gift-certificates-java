package com.epam.esm.gift.service.impl

import com.epam.esm.gift.dto.TagDto
import com.epam.esm.gift.localization.error.EntityDuplicateException
import com.epam.esm.gift.localization.error.EntityNotFoundException
import com.epam.esm.gift.mapper.TagEntityMapper
import com.epam.esm.gift.model.Tag
import com.epam.esm.gift.repository.TagRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import java.util.Optional

@org.junit.jupiter.api.Tag("unit")
@ExtendWith(MockitoExtension::class)
internal class TagServiceImplTest {

    @Mock private lateinit var tagRepository: TagRepository
    @Mock private lateinit var tagConverter: TagEntityMapper

    @InjectMocks
    private lateinit var tagService: TagServiceImpl

    @Test
    fun `should return an existing tag represented as DTO`() {
        // given
        val id = 1L
        val name = "test"
        val tag = Tag.builder().id(id).name(name).build()
        val tagDTO = TagDto(id, name)

        whenever(tagRepository.findById(anyLong())) doReturn Optional.of(tag)
        whenever(tagConverter.mapEntityToDto(tag)) doReturn tagDTO

        // when
        val result = tagService.findOne(id)

        // then
        assertThat(result)
            .isNotNull
            .isEqualTo(tagDTO)
    }

    @Test
    fun `should throw EntityNotFoundException within tag lookup by id`() {
        // given
        val id = 1L

        whenever(tagRepository.findById(anyLong())) doReturn Optional.empty()

        // when
        val action = ThrowingCallable { tagService.findOne(id) }

        // then
        assertThatExceptionOfType(EntityNotFoundException::class.java).isThrownBy(action)
    }

    @Test
    fun `should return existing tags represented as DTOs`() {
        // given
        val id = 1L
        val name = "test"
        val tag = Tag.builder().id(id).name(name).build()
        val tagDTO = TagDto(id, name)

        whenever(tagRepository.findAll()) doReturn listOf(tag)
        whenever(tagConverter.mapEntityToDto(tag)) doReturn tagDTO

        // when
        val result = tagService.findAll()

        // then
        assertThat(result)
            .isNotNull
            .hasSize(1)
            .contains(tagDTO)
    }

    @Test
    fun `should create tag entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val tagDto = TagDto(null, testName)
        val tagEntity = Tag(null, testName)
        val expectedTagEntity = tagEntity.copy().id(testId).build()
        val expectedTagDto = tagDto.copy().id(testId).build()

        whenever(tagConverter.mapDtoToEntity(tagDto)) doReturn tagEntity
        whenever(tagRepository.findTagByName(testName)) doReturn Optional.empty()
        whenever(tagRepository.create(tagEntity)) doReturn expectedTagEntity
        whenever(tagConverter.mapEntityToDto(expectedTagEntity)) doReturn expectedTagDto

        // when
        val createdTag = tagService.create(tagDto)

        // then
        assertThat(createdTag)
            .isNotNull
            .isEqualTo(expectedTagDto)
    }

    @Test
    fun `should throw EntityDuplicateException within create of tag entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val tagDto = TagDto(null, testName)
        val tagEntity = Tag(testId, testName)

        whenever(tagRepository.findTagByName(testName)) doReturn Optional.of(tagEntity)

        // when
        val action = ThrowingCallable { tagService.create(tagDto) }

        // then
        assertThatExceptionOfType(EntityDuplicateException::class.java).isThrownBy(action)
    }

    @Test
    fun `should update tag entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val tagDto = TagDto(null, testName)
        val tagEntity = Tag(null, testName)
        val expectedTagEntity = tagEntity.copy().id(testId).build()
        val expectedTagDto = tagDto.copy().id(testId).build()

        whenever(tagConverter.mapDtoToEntity(tagDto)) doReturn tagEntity
        whenever(tagRepository.findTagByName(testName)) doReturn Optional.empty()
        whenever(tagRepository.update(tagEntity)) doReturn expectedTagEntity
        whenever(tagConverter.mapEntityToDto(expectedTagEntity)) doReturn expectedTagDto

        // when
        val createdTag = tagService.update(tagDto)

        // then
        assertThat(createdTag)
            .isNotNull
            .isEqualTo(expectedTagDto)
    }

    @Test
    fun `should throw EntityDuplicateException within update of tag entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val tagDto = TagDto(null, testName)
        val tagEntity = Tag(testId, testName)

        whenever(tagRepository.findTagByName(testName)) doReturn Optional.of(tagEntity)

        // when
        val action = ThrowingCallable { tagService.update(tagDto) }

        // then
        assertThatExceptionOfType(EntityDuplicateException::class.java).isThrownBy(action)
    }

    @Test
    fun `should delete tag by id`() {
        // given
        val testId = 1L
        val testName = "test"
        val tagEntity = Tag(testId, testName)

        whenever(tagRepository.findById(testId)) doReturn Optional.of(tagEntity)

        // when
        tagService.deleteById(testId)

        // then
        verify(tagRepository).delete(tagEntity)
    }

    @Test
    fun `should throw EntityNotFoundException within delete tag by id`() {
        // given
        val testId = 1L

        whenever(tagRepository.findById(testId)) doReturn Optional.empty()

        // when
        val action = ThrowingCallable { tagService.deleteById(testId) }

        // then
        assertThatExceptionOfType(EntityNotFoundException::class.java).isThrownBy(action)
    }
}