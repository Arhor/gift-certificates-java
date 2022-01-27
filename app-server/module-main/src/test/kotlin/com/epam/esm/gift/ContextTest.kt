package com.epam.esm.gift

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@Tag("unit")
@SpringJUnitConfig(ContextTest.EmptyContextTestConfig::class)
internal class ContextTest {

    @Configuration
    class EmptyContextTestConfig

    @Autowired
    private lateinit var context: ApplicationContext

    @Test
    fun `application context should be loaded successfully`() {
        assertThat(context)
            .isNotNull
    }
}
