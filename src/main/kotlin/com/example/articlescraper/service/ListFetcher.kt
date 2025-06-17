package com.example.articlescraper.service

import com.example.articlescraper.Article

/**
 * Crawls pages to find article URLs.
 */
interface ListFetcher {
    /**
     * Fetches the list of article URLs from the site.
     */
    fun fetch(): List<Article>
}
