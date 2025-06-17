package com.example.articlescraper

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Repository for [Article] documents.
 */
interface ArticleRepository : MongoRepository<Article, String>
