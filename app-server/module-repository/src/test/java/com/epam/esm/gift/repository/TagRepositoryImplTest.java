package com.epam.esm.gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.epam.esm.gift.model.Certificate;
import com.epam.esm.gift.model.Tag;
import com.epam.esm.gift.repository.impl.CertificateRepositoryImpl;
import com.epam.esm.gift.repository.impl.TagRepositoryImpl;

@Transactional
@ExtendWith(SpringExtension.class)
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
class TagRepositoryImplTest {

    @Autowired
    private TagRepositoryImpl repository;

    @Autowired
    private CertificateRepositoryImpl certificateRepository;

    @Test
    void should_correctly_persist_and_find_new_tag() {
        // given
        var initialTag = new Tag();
        initialTag.setName("Test tag");

        // when
        var createdTag = repository.create(initialTag);
        var tagFromRepository = repository.findById(createdTag.getId());

        // then
        assertThat(tagFromRepository)
            .isNotNull()
            .isNotEmpty()
            .contains(initialTag);
    }

    @Test
    void should_correctly_update_an_existing_tag() {
        // given
        var initialTag = new Tag();
        initialTag.setName("Test tag");
        var updatedName = "Updated test tag name";

        // when
        var createdTag = repository.create(initialTag);
        var updatedTag = repository.update(createdTag.copy().name(updatedName).build());

        // then
        assertThat(updatedTag.getName())
            .isNotEqualTo(createdTag.getName())
            .isEqualTo(updatedName);
    }

    @Test
    void read_all_should_return_list_of_all_existing_tags() {
        // given
        var initialTags = List.of(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build()
        );

        initialTags.forEach(repository::create);

        // when
        var allExistingTags = repository.findAll();

        // then
        assertThat(allExistingTags)
            .hasSameSizeAs(initialTags)
            .containsAll(initialTags);
    }

    @Test
    void should_delete_existing_tag() {
        // given
        var initialTag = Tag.builder().name("Test tag").build();

        // when
        var createdTag = repository.create(initialTag);
        repository.delete(createdTag);
        var tagFromRepository = repository.findById(createdTag.getId());

        // then
        assertThat(tagFromRepository)
            .isEmpty();
    }

    @Test
    void should_delete_existing_tag_by_its_id() {
        // given
        var initialTag = Tag.builder().name("Test tag").build();

        // when
        var createdTag = repository.create(initialTag);
        repository.deleteById(createdTag.getId());
        var tagFromRepository = repository.findById(createdTag.getId());

        // then
        assertThat(tagFromRepository)
            .isEmpty();
    }

    @Test
    void should_correctly_add_tags_to_an_existing_certificate() {
        // given
        var initialTags = List.of(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build()
        );

        for (var initialTag : initialTags) {
            repository.create(initialTag);
        }

        var certificate = certificateRepository.create(
            Certificate.builder()
                .name("Test certificate name")
                .description("Test certificate description")
                .price(new BigDecimal("1.00"))
                .duration(30).build()
        );

        // when
        repository.addTagsToCertificate(certificate.getId(), initialTags);
        var tagsByCertificateId = repository.findTagsByCertificateId(certificate.getId());

        // then
        assertThat(tagsByCertificateId)
            .isEqualTo(initialTags);
    }

    @Test
    void should_correctly_remove_tags_from_an_existing_certificate() {
        // given
        var initialTags = List.of(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build()
        );

        initialTags.forEach(repository::create);

        var certificate = certificateRepository.create(
            Certificate.builder()
                .name("Test certificate name")
                .description("Test certificate description")
                .price(new BigDecimal("1.00"))
                .duration(30)
                .build()
        );

        // when
        repository.addTagsToCertificate(certificate.getId(), initialTags);
        repository.removeAllTagsFromCertificate(certificate.getId());
        var tagsByCertificateId = repository.findTagsByCertificateId(certificate.getId());

        // then
        assertThat(tagsByCertificateId)
            .isEmpty();
    }
}