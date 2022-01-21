package com.epam.esm.gift

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTag
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures.layeredArchitecture

@ArchTag("architecture")
@AnalyzeClasses(packages = ["com.epam.esm.gift"], importOptions = [DoNotIncludeTests::class])
internal class LayeredArchitectureTest {

    @ArchTest
    fun `correct layered architecture should be observed`(applicationClasses: JavaClasses) {
        layeredArchitecture()
            .layer("Web").definedBy("com.epam.esm.gift.web..")
            .layer("Service").definedBy("com.epam.esm.gift.service..")
            .layer("Repository").definedBy("com.epam.esm.gift.repository..")
            .whereLayer("Web").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Web")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
            .check(applicationClasses)
    }
}
