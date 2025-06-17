package com.example.articlescraper.service

/**
 * Publishes Pub/Sub messages for processed articles.
 */
interface PubSubPublisher {
    /**
     * Publishes messages containing [articleIds] to Pub/Sub.
     */
    fun publish(articleIds: List<String>)
}
