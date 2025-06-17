package com.example.articlescraper

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Article document stored in MongoDB.
 */
@Document(collection = "articles")
data class Article(
    @Id
    val id: String? = null,
    val url: String,
    var title: String? = null,
    var html: String? = null,
    var heroImageUrl: String? = null,
    var classification: String? = null,
    val translations: MutableMap<String, String> = mutableMapOf()
)
