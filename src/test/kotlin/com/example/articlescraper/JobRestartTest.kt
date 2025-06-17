package com.example.articlescraper

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

/**
 * Test that job can restart using Postgres container.
 */
@Testcontainers
@ExtendWith(SpringExtension::class)
@SpringBootTest
class JobRestartTest {

    companion object {
        @Container
        private val postgres = PostgreSQLContainer<Nothing>("postgres:15-alpine")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }
    }

    @Autowired
    lateinit var jobLauncher: JobLauncher

    @Autowired
    lateinit var job: Job

    @Test
    fun testJobRuns() {
        val params = JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters()
        jobLauncher.run(job, params)
        // second run should restart
        jobLauncher.run(job, params)
    }
}
