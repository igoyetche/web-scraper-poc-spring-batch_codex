package com.example.articlescraper.service.impl

import com.example.articlescraper.Article
import com.example.articlescraper.service.Classifier
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

/**
 * Stub classifier that assigns a dummy category.
 */
@Service
class ClassifierImpl : Classifier {
    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun classify(article: Article): Article {
        article.classification = "tech"
        log.info("Classified article {}", article.id)
        return article
    }
}
