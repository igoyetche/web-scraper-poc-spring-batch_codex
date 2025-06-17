package com.example.articlescraper.service

import com.example.articlescraper.Article

/**
 * Downloads article HTML and images.
 */
interface ArticleDownloader {
    /**
     * Downloads the HTML and hero image for the given [article].
     */
    fun download(article: Article): Article
}
