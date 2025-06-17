package com.example.articlescraper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.task.configuration.EnableTask
import org.springframework.retry.annotation.EnableRetry

/**
 * Spring Boot application entry point.
 */
@EnableRetry
@EnableTask
@SpringBootApplication
class ArticleScraperApplication

/**
 * Application main entry point.
 */
fun main(args: Array<String>) {
    runApplication<ArticleScraperApplication>(*args)
}
