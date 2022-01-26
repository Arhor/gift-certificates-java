package com.epam.esm.gift.repository

import com.epam.esm.gift.model.Certificate
import com.epam.esm.gift.model.Tag
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.from
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import org.junit.jupiter.api.Tag as TestTag

@TestTag("unit")
@Transactional
@SpringJUnitConfig(TestDatabaseConfig::class)
@Testcontainers(disabledWithoutDocker = true)
internal class TagRepositoryTest {

    @Autowired
    private lateinit var repository: TagRepository

    @Autowired
    private lateinit var certificateRepository: CertificateRepository

    @Test
    fun `should persist and find new tag`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }

        // when
        val createdTag = repository.create(initialTag)
        val tagFromRepository = repository.findById(createdTag.id)

        // then
        assertThat(tagFromRepository)
            .isNotNull
            .isNotEmpty
            .contains(initialTag)
    }

    @Test
    fun `should update an existing tag`() {
        // given
        val initialTag = Tag().apply { name = "Test tag" }
        val updatedName = "Updated test tag name"

        // when
        val createdTag = repository.create(initialTag)
        val updatedTag = repository.update(createdTag.copy().name(updatedName).build())

        // then
        assertThat(updatedTag.name)
            .isNotNull
            .isNotEqualTo(createdTag.name)
            .isEqualTo(updatedName)
    }

    @Test
    fun `should return list of all existing tags`() {
        // given
        val initialTags = listOf(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build(),
        )

        initialTags.forEach(repository::create)

        // when
        val allExistingTags = repository.findAll()

        // then
        assertThat(allExistingTags)
            .isNotNull
            .hasSameSizeAs(initialTags)
            .containsAll(initialTags)
    }

    @Test
    fun `should delete existing tag`() {
        // given
        val initialTag = Tag.builder().name("Test tag").build()

        // when
        val createdTag = repository.create(initialTag)
        repository.delete(createdTag)
        val tagFromRepository = repository.findById(createdTag.id)

        // then
        assertThat(tagFromRepository)
            .isNotNull
            .isEmpty
    }

    @Test
    fun `should delete existing tag by its id`() {
        // given
        val initialTag = Tag.builder().name("Test tag").build()

        // when
        val createdTag = repository.create(initialTag)
        repository.deleteById(createdTag.id)
        val tagFromRepository = repository.findById(createdTag.id)

        // then
        assertThat(tagFromRepository)
            .isNotNull
            .isEmpty
    }

    @Test
    fun `should add tags to an existing certificate`() {
        // given
        val initialTags = listOf(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build()
        )

        for (initialTag in initialTags) {
            repository.create(initialTag)
        }

        val certificate = certificateRepository.create(
            Certificate.builder()
                .name("Test certificate name")
                .description("Test certificate description")
                .price(BigDecimal("1.00"))
                .duration(30)
                .build()
        )

        // when
        repository.addTagsToCertificate(certificate.id, initialTags)
        val tagsByCertificateId = repository.findTagsByCertificateId(certificate.id)

        // then
        assertThat(tagsByCertificateId)
            .isNotNull
            .isEqualTo(initialTags)
    }

    @Test
    fun `should remove tags from an existing certificate`() {
        // given
        val initialTags = listOf(
            Tag.builder().name("Test tag #1").build(),
            Tag.builder().name("Test tag #2").build(),
            Tag.builder().name("Test tag #3").build()
        )

        initialTags.forEach(repository::create)

        val certificate = certificateRepository.create(
            Certificate.builder()
                .name("Test certificate name")
                .description("Test certificate description")
                .price(BigDecimal("1.00"))
                .duration(30)
                .build()
        )

        // when
        repository.addTagsToCertificate(certificate.id, initialTags)
        repository.removeAllTagsFromCertificate(certificate.id)
        val tagsByCertificateId = repository.findTagsByCertificateId(certificate.id)

        // then
        assertThat(tagsByCertificateId)
            .isNotNull
            .isEmpty()
    }

    @Test
    fun `should find tag by its name`() {
        // given
        val expectedName = "Test tag #1"

        repository.create(Tag().apply { name = expectedName })

        // when
        val tag = repository.findTagByName(expectedName)

        // then
        assertThat(tag)
            .isNotNull
            .isPresent
            .get()
            .returns(expectedName, from(Tag::getName))
    }

    @Test
    fun `should find tags by their names`() {
        // given
        val initialTagNames = listOf(
            "Test tag #1",
            "Test tag #2",
            "Test tag #3"
        )

        initialTagNames.map { Tag().apply { name = it } }.forEach(repository::create)

        // when
        val tags = repository.findTagByNames(initialTagNames)

        // then
        assertThat(tags)
            .isNotNull
            .hasSameSizeAs(initialTagNames)
            .allMatch { tag -> initialTagNames.contains(tag.name) }
    }
}