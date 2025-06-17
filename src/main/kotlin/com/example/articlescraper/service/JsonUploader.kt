package com.example.articlescraper.service

import com.example.articlescraper.Article

/**
 * Uploads article JSON files to GCS.
 */
interface JsonUploader {
    /**
     * Uploads the provided [articles] to a storage bucket.
     */
    fun upload(articles: List<Article>)
}
