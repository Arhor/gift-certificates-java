package com.epam.esm.gift.repository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.epam.esm.gift.model.Certificate;

@Transactional
@SpringJUnitConfig(TestDatabaseConfig.class)
@Testcontainers(disabledWithoutDocker = true)
class CertificateRepositoryTest {

    @Autowired
    private CertificateRepository repository;

    @Test
    void should_correctly_persist_and_find_new_certificate() {
        // given
        var initialCertificate = createTestCertificate("Test Certificate");

        // when
        var createdCertificate = repository.create(initialCertificate);
        var certificateFromRepository = repository.findById(createdCertificate.getId());

        // then
        assertThat(certificateFromRepository).isNotNull().isNotEmpty();

        var certificate = certificateFromRepository.get();

        assertThat(certificate.getName()).isEqualTo(initialCertificate.getName());
        assertThat(certificate.getDescription()).isEqualTo(initialCertificate.getDescription());
        assertThat(certificate.getPrice()).isEqualTo(initialCertificate.getPrice());
        assertThat(certificate.getDuration()).isEqualTo(initialCertificate.getDuration());
    }

    @Test
    void should_correctly_update_an_existing_certificate() {
        // given
        var initialCertificate = createTestCertificate("Test Certificate");
        var updatedName = "Updated test Certificate name";

        // when
        var createdCertificate = repository.create(initialCertificate);
        var updatedCertificate = repository.update(createdCertificate.copy().name(updatedName).build());

        // then
        assertThat(updatedCertificate.getName())
            .isNotEqualTo(createdCertificate.getName())
            .isEqualTo(updatedName);
    }

    @Test
    void readAll_should_return_list_of_all_existing_certificates() {
        // given
        var initialCertificates = List.of(
            createTestCertificate("Test Certificate #1"),
            createTestCertificate("Test Certificate #2"),
            createTestCertificate("Test Certificate #3")
        );

        for (var initialCertificate : initialCertificates) {
            repository.create(initialCertificate);
        }

        // when
        var allExistingCertificates = repository.findAll().stream().map(Certificate::getId).collect(toList());

        // then
        assertThat(allExistingCertificates)
            .hasSameSizeAs(initialCertificates)
            .containsAll(initialCertificates.stream().map(Certificate::getId).collect(toList()));
    }

    @Test
    void should_delete_existing_certificate() {
        // given
        var initialCertificate = createTestCertificate("Test Certificate");

        // when
        var createdCertificate = repository.create(initialCertificate);
        repository.delete(createdCertificate);
        var certificateFromRepository = repository.findById(createdCertificate.getId());

        // then
        assertThat(certificateFromRepository)
            .isEmpty();
    }

    @Test
    void should_delete_existing_certificate_by_its_id() {
        // given
        var initialCertificate = createTestCertificate("Test certificate");

        // when
        var createdCertificate = repository.create(initialCertificate);
        repository.deleteById(createdCertificate.getId());
        var certificateFromRepository = repository.findById(createdCertificate.getId());

        // then
        assertThat(certificateFromRepository)
            .isEmpty();
    }

    private Certificate createTestCertificate(final String name) {
        var certificate = new Certificate();
        certificate.setName(name);
        certificate.setDescription("Test certificate description");
        certificate.setPrice(new BigDecimal("1.00"));
        certificate.setDuration(30);
        return certificate;
    }
}
