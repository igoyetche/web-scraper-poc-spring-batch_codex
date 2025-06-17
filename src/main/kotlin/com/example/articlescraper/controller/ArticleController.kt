package com.example.articlescraper.controller

import com.example.articlescraper.Article
import com.example.articlescraper.ArticleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST API for retrieving articles.
 */
@RestController
@RequestMapping("/api/articles")
class ArticleController(private val repository: ArticleRepository) {

    /**
     * Returns all articles.
     */
    @GetMapping
    fun all(): List<Article> = repository.findAll()

    /**
     * Returns a single article by [id] or `null` if it does not exist.
     */
    @GetMapping("/{id}")
    fun byId(@PathVariable id: String): Article? = repository.findById(id).orElse(null)
}
