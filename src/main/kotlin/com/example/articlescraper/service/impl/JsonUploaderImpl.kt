package com.example.articlescraper.service.impl

import com.example.articlescraper.Article
import com.example.articlescraper.service.JsonUploader
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path

/**
 * Stub uploader writing JSON files to a local temp directory.
 */
@Service
class JsonUploaderImpl(
    private val objectMapper: ObjectMapper,
    @Value("\${scraper.bucketName}") private val bucket: String
) : JsonUploader {

    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun upload(articles: List<Article>) {
        val dir = Files.createTempDirectory(bucket)
        articles.forEach { a ->
            val file = dir.resolve("${a.id}.json")
            Files.writeString(file, objectMapper.writeValueAsString(a))
        }
        log.info("Uploaded {} articles to {}", articles.size, dir)
    }
}
