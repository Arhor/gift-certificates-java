package com.epam.esm.gift.repository

import com.epam.esm.gift.repository.model.Certificate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@Tag("unit")
@Transactional
@SpringJUnitConfig(TestDatabaseConfig::class)
@Testcontainers(disabledWithoutDocker = true)
internal class CertificateRepositoryTest {

    @Autowired
    private lateinit var repository: CertificateRepository

    @Test
    fun `should persist and find new certificate`() {
        // given
        val initialCertificate = createTestCertificate("Test Certificate")

        // when
        val createdCertificate = repository.create(initialCertificate)
        val certificateFromRepository = repository.findById(createdCertificate.id)

        // then
        assertThat(certificateFromRepository).isNotNull.isNotEmpty

        val certificate = certificateFromRepository.get()

        assertThat(certificate.name).isEqualTo(initialCertificate.name)
        assertThat(certificate.description).isEqualTo(initialCertificate.description)
        assertThat(certificate.price).isEqualTo(initialCertificate.price)
        assertThat(certificate.duration).isEqualTo(initialCertificate.duration)
    }

    @Test
    fun `should update an existing certificate`() {
        // given
        val initialCertificate = createTestCertificate("Test Certificate")
        val updatedName = "Updated test Certificate name"

        // when
        val createdCertificate = repository.create(initialCertificate)
        val updatedCertificate = repository.update(createdCertificate.copy().name(updatedName).build())

        // then
        assertThat(updatedCertificate.name).isNotEqualTo(createdCertificate.name).isEqualTo(updatedName)
    }

    @Test
    fun `should return list of all existing certificates`() {
        // given
        val initialCertificates = listOf(createTestCertificate("Test Certificate #1"),
            createTestCertificate("Test Certificate #2"),
            createTestCertificate("Test Certificate #3"))

        for (initialCertificate in initialCertificates) {
            repository.create(initialCertificate)
        }

        // when
        val allExistingCertificates = repository.findAll().map(Certificate::getId)

        // then
        assertThat(allExistingCertificates).hasSameSizeAs(initialCertificates)
            .containsAll(initialCertificates.map(Certificate::getId))
    }

    @Test
    fun `should delete existing certificate`() {
        // given
        val initialCertificate = createTestCertificate("Test Certificate")

        // when
        val createdCertificate = repository.create(initialCertificate)
        repository.delete(createdCertificate)
        val certificateFromRepository = repository.findById(createdCertificate.id)

        // then
        assertThat(certificateFromRepository).isEmpty
    }

    @Test
    fun `should delete existing certificate by id`() {
        // given
        val initialCertificate = createTestCertificate("Test certificate")

        // when
        val certificateFromRepository = repository.create(initialCertificate).let {
            repository.deleteById(it.id)
            repository.findById(it.id)
        }

        // then
        assertThat(certificateFromRepository).isEmpty
    }

    private fun createTestCertificate(testName: String) = Certificate()
        .apply {
        name = testName
        description = "Test certificate description"
        price = BigDecimal("1.00")
        duration = 30
    }
}
