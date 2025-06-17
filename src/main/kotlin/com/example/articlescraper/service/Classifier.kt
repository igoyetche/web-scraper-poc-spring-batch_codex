package com.example.articlescraper.service

import com.example.articlescraper.Article

/**
 * Stub classification service.
 */
interface Classifier {
    /**
     * Classifies the given [article] and returns the updated instance.
     */
    fun classify(article: Article): Article
}
