package com.example.articlescraper.service.impl

import com.example.articlescraper.Article
import com.example.articlescraper.service.ArticleDownloader
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

/**
 * Downloads article HTML and images using Jsoup.
 */
@Service
class ArticleDownloaderImpl : ArticleDownloader {
    private val log = LoggerFactory.getLogger(javaClass)

    @Retryable(backoff = Backoff(delay = 1000, multiplier = 2.0), maxAttempts = 3)
    /**
     * {@inheritDoc}
     */
    override fun download(article: Article): Article {
        val doc = Jsoup.connect(article.url).get()
        article.title = doc.selectFirst("head > title")?.text()
        article.html = doc.outerHtml()
        val img = doc.selectFirst("img.wp-post-image")
        article.heroImageUrl = img?.absUrl("src")
        log.info("Downloaded article {}", article.url)
        return article
    }
}
