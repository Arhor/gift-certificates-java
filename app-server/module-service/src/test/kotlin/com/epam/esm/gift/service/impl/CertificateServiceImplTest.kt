package com.epam.esm.gift.service.impl

import com.epam.esm.gift.dto.CertificateDto
import com.epam.esm.gift.localization.error.EntityNotFoundException
import com.epam.esm.gift.mapper.CertificateEntityMapper
import com.epam.esm.gift.mapper.TagEntityMapper
import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.repository.CertificateRepository
import com.epam.esm.gift.repository.TagRepository
import org.assertj.core.api.Assertions.assertThat
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

    @Mock private lateinit var certificateRepository: CertificateRepository
    @Mock private lateinit var certificateMapper: CertificateEntityMapper
    @Mock private lateinit var tagRepository: TagRepository
    @Mock private lateinit var tagMapper: TagEntityMapper

    @InjectMocks
    private lateinit var certificateService: CertificateServiceImpl

    @Test
    fun `should return an existing certificate represented as DTO`() {
        // given
        val testId = 1L
        val testName = "test"
        val tag = Certificate().apply { id = testId; name = testName }
        val tagDTO = CertificateDto.builder().id(testId).name(testName).build()

        whenever(certificateRepository.findById(anyLong())) doReturn Optional.of(tag)
        whenever(certificateMapper.mapEntityToDto(tag)) doReturn tagDTO

        // when
        val result = certificateService.findOne(testId)

        // then
        assertThat(result)
            .isNotNull
            .isEqualTo(tagDTO)
    }

    @Test
    fun `should throw EntityNotFoundException within certificate lookup by id`() {
        // given
        whenever(certificateRepository.findById(anyLong())) doReturn Optional.empty()

        // when
        val action = ThrowingCallable { certificateService.findOne(1L) }

        // then
        assertThatExceptionOfType(EntityNotFoundException::class.java).isThrownBy(action)
    }

    @Test
    fun `should return existing certificates represented as DTOs`() {
        // given
        val id = 1L
        val name = "test"
        val tag = Certificate.builder().id(id).name(name).build()
        val tagDTO = CertificateDto.builder().id(id).name(name).build()

        whenever(certificateRepository.findAll()) doReturn listOf(tag)
        whenever(certificateMapper.mapEntityToDto(tag)) doReturn tagDTO

        // when
        val result = certificateService.findAll()

        // then
        assertThat(result)
            .isNotNull
            .hasSize(1)
            .contains(tagDTO)
    }

    @Test
    fun `should create certificate entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val certificateDto = CertificateDto.builder().id(testId).name(testName).build()
        val certificate = Certificate.builder().id(testId).name(testName).build()

        whenever(certificateMapper.mapDtoToEntity(certificateDto)).thenReturn(certificate)
        whenever(certificateMapper.mapEntityToDto(certificate)).thenReturn(certificateDto)
        whenever(certificateRepository.create(certificate)).thenReturn(certificate)

        // when
        val updatedCertificate = certificateService.create(certificateDto)

        // then
        assertThat(updatedCertificate)
            .isNotNull
            .isEqualTo(certificateDto)
    }

    @Test
    fun `should update certificate entity`() {
        // given
        val testId = 1L
        val testName = "test"
        val certificateDto = CertificateDto.builder().id(testId).name(testName).build()
        val certificate = Certificate.builder().id(testId).name(testName).build()

        whenever(certificateMapper.mapDtoToEntity(certificateDto)).thenReturn(certificate)
        whenever(certificateMapper.mapEntityToDto(certificate)).thenReturn(certificateDto)
        whenever(certificateRepository.update(certificate)).thenReturn(certificate)

        // when
        val updatedCertificate = certificateService.update(certificateDto)

        // then
        assertThat(updatedCertificate)
            .isNotNull
            .isEqualTo(certificateDto)
    }

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
