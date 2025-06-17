package com.example.articlescraper.service.impl

import com.example.articlescraper.service.PubSubPublisher
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

/**
 * Stub publisher that logs published IDs.
 */
@Service
class PubSubPublisherImpl : PubSubPublisher {
    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun publish(articleIds: List<String>) {
        log.info("Published IDs to Pub/Sub: {}", articleIds)
    }
}
