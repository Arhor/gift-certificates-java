package com.epam.esm.gift.service.impl

import com.epam.esm.gift.localization.error.EntityNotFoundException
import com.epam.esm.gift.mapper.CertificateEntityMapper
import com.epam.esm.gift.mapper.TagEntityMapper
import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.CertificateRepository
import com.epam.esm.gift.repository.TagRepository
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.junit.jupiter.api.Tag
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

@Tag("unit")
@ExtendWith(MockitoExtension::class)
internal class CertificateServiceImplTest {

    @Mock
    private lateinit var certificateRepository: CertificateRepository

    @Mock
    private lateinit var certificateMapper: CertificateEntityMapper

    @Mock
    private lateinit var tagRepository: TagRepository

    @Mock
    private lateinit var tagMapper: TagEntityMapper

    @InjectMocks
    private lateinit var certificateService: CertificateServiceImpl

//    @Test
//    void should_return_an_existing_tag_represented_as_DTO() {
//        // given
//        var id = 1L;
//        var name = "test";
//        var tag = Tag.builder().id(id).name(name).build();
//        var tagDTO = new TagDto(id, name);
//
//        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(tag));
//        when(certificateConverter.mapEntityToDto(tag)).thenReturn(tagDTO);
//
//        // when
//        var result = tagService.findOne(id);
//
//        // then
//        assertThat(result)
//            .isNotNull()
//            .isEqualTo(tagDTO);
//    }
//
//    @Test
//    void should_throw_EntityNotFoundException_within_tag_lookup_by_id() {
//        // given
//        var id = 1L;
//
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // when
//        ThrowingCallable action = () -> tagService.findOne(id);
//
//        // then
//        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(action);
//    }
//
//    @Test
//    void should_return_existing_tags_represented_as_DTOs() {
//        // given
//        var id = 1L;
//        var name = "test";
//        var tag = Tag.builder().id(id).name(name).build();
//        var tagDTO = new TagDto(id, name);
//
//        when(tagRepository.findAll()).thenReturn(List.of(tag));
//        when(tagConverter.mapEntityToDto(tag)).thenReturn(tagDTO);
//
//        // when
//        var result = tagService.findAll();
//
//        // then
//        assertThat(result)
//            .isNotNull()
//            .hasSize(1)
//            .contains(tagDTO);
//    }
//
//    @Test
//    void should_create_tag_entity() {
//        // given
//        var testId = 1L;
//        var testName = "test";
//        var tagDto = new TagDto(null, testName);
//        var tagEntity = new Tag(null, testName);
//        var expectedTagEntity = tagEntity.copy().id(testId).build();
//        var expectedTagDto = tagDto.copy().id(testId).build();
//
//        when(tagConverter.mapDtoToEntity(tagDto)).thenReturn(tagEntity);
//        when(tagRepository.findTagByName(testName)).thenReturn(Optional.empty());
//        when(tagRepository.create(tagEntity)).thenReturn(expectedTagEntity);
//        when(tagConverter.mapEntityToDto(expectedTagEntity)).thenReturn(expectedTagDto);
//
//
//        // when
//        var createdTag = tagService.create(tagDto);
//
//        // then
//        assertThat(createdTag)
//            .isNotNull()
//            .isEqualTo(expectedTagDto);
//    }
//
//    @Test
//    void should_update_tag_entity() {
//        // given
//        var testId = 1L;
//        var testName = "test";
//        var certificateDto = CertificateDto.builder().name(testName).build();
//        var certificate = Certificate.builder().name(testName).build();
//        var expectedCertificate = certificate.copy().id(testId).build();
//        var expectedCertificateDto = certificateDto.copy().id(testId).build();
//
//        when(certificateMapper.mapDtoToEntity(certificateDto)).thenReturn(certificate);
//        when(tagRepository.findTagByName(testName)).thenReturn(Optional.empty());
//        when(tagRepository.update(certificate)).thenReturn(expectedCertificate);
//        when(tagConverter.mapEntityToDto(expectedCertificate)).thenReturn(expectedCertificateDto);
//
//
//        // when
//        var updatedCertificate = certificateService.update(certificateDto);
//
//        // then
//        assertThat(updatedCertificate)
//            .isNotNull()
//            .isEqualTo(expectedCertificateDto);
//    }

    @Test
    fun `should delete certificate by id`() {
        // given
        val testId = 1L
        val certificate = Certificate().apply { id = testId; name = "test" }

        whenever(certificateRepository.findById(testId)) doReturn Optional.of(certificate)

        // when
        certificateService.deleteById(testId)

        // then
        verify(certificateRepository).delete(certificate)
    }

    @Test
    fun `should throw EntityNotFoundException when delete certificate by id`() {
        // given
        whenever(certificateRepository.findById(anyLong())) doReturn Optional.empty()

        // when
        val action = ThrowingCallable { certificateService.deleteById(1L) }

        // then
        assertThatExceptionOfType(EntityNotFoundException::class.java).isThrownBy(action)
    }
}
