package com.example.articlescraper.controller

import org.springframework.batch.core.Job
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Admin endpoint to trigger the batch job manually.
 */
@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val jobLauncher: JobLauncher,
    private val job: Job
) {
    /**
     * Manually triggers the batch job.
     */
    @PostMapping("/refresh")
    fun refresh() {
        jobLauncher.run(job, org.springframework.batch.core.JobParameters())
    }
}
