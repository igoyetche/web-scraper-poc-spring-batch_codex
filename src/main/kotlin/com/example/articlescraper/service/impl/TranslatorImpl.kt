package com.example.articlescraper.service.impl

import com.example.articlescraper.Article
import com.example.articlescraper.service.Translator
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

/**
 * Stub translator that copies the title with language suffix.
 */
@Service
class TranslatorImpl : Translator {
    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun translate(article: Article, lang: String): Article {
        val translated = (article.title ?: "") + " ($lang)"
        article.translations[lang] = translated
        log.info("Translated article {} to {}", article.id, lang)
        return article
    }
}
