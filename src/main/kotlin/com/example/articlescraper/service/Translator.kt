package com.example.articlescraper.service

import com.example.articlescraper.Article

/**
 * Stub translation service.
 */
interface Translator {
    /**
     * Translates the [article] to the specified [lang].
     */
    fun translate(article: Article, lang: String): Article
}
