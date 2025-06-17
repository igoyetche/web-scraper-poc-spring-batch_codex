package com.example.articlescraper.service.impl

import com.example.articlescraper.Article
import com.example.articlescraper.service.ListFetcher
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

/**
 * Fetches article list from the website.
 */
@Service
class ListFetcherImpl(
    @Value("\${scraper.delayMs:1000}") private val delayMs: Long
) : ListFetcher {

    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun fetch(): List<Article> {
        val articles = mutableListOf<Article>()
        var page = 1
        while (true) {
            val url = "https://thenewstack.io/page/$page/"
            val doc = Jsoup.connect(url).get()
            val links = doc.select("article h2 a")
            if (links.isEmpty()) break
            links.forEach { a ->
                articles += Article(url = a.absUrl("href"))
            }
            page++
            Thread.sleep(delayMs)
        }
        log.info("Fetched {} article urls", articles.size)
        return articles
    }
}
