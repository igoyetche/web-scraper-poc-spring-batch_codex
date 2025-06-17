package com.example.articlescraper

import com.example.articlescraper.service.*
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring Batch job configuration.
 */
@Configuration
@EnableBatchProcessing
class ArticleJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val repository: ArticleRepository,
    private val listFetcher: ListFetcher,
    private val downloader: ArticleDownloader,
    private val classifier: Classifier,
    private val translator: Translator,
    private val uploader: JsonUploader,
    private val publisher: PubSubPublisher
) {
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Defines the batch job composed of all scraping steps.
     */
    @Bean
    fun articleJob(): Job = jobBuilderFactory.get("articleJob")
        .incrementer(RunIdIncrementer())
        .start(fetchStep())
        .next(downloadStep())
        .next(classifyStep())
        .next(translateStep())
        .next(uploadStep())
        .next(publishStep())
        .build()

    /**
     * Crawls the website for new article URLs.
     */
    @Bean
    fun fetchStep(): Step = stepBuilderFactory.get("FETCH_LIST")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting FETCH_LIST")
            val articles = listFetcher.fetch()
            repository.saveAll(articles)
            log.info("Completed FETCH_LIST")
            RepeatStatus.FINISHED
        }).build()

    /**
     * Downloads HTML content and images for all articles.
     */
    @Bean
    fun downloadStep(): Step = stepBuilderFactory.get("DOWNLOAD_ARTICLES")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting DOWNLOAD_ARTICLES")
            val articles = repository.findAll().filter { it.html == null }
            articles.forEach { repository.save(downloader.download(it)) }
            log.info("Completed DOWNLOAD_ARTICLES")
            RepeatStatus.FINISHED
        }).build()

    /**
     * Applies article classification.
     */
    @Bean
    fun classifyStep(): Step = stepBuilderFactory.get("CLASSIFY")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting CLASSIFY")
            val articles = repository.findAll()
            articles.forEach { repository.save(classifier.classify(it)) }
            log.info("Completed CLASSIFY")
            RepeatStatus.FINISHED
        }).build()

    /**
     * Adds translations for each article.
     */
    @Bean
    fun translateStep(): Step = stepBuilderFactory.get("TRANSLATE")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting TRANSLATE")
            val articles = repository.findAll()
            val langs = listOf("es", "pt", "fr")
            articles.forEach { a ->
                langs.forEach { lang -> translator.translate(a, lang) }
                repository.save(a)
            }
            log.info("Completed TRANSLATE")
            RepeatStatus.FINISHED
        }).build()

    /**
     * Uploads processed articles as JSON to storage.
     */
    @Bean
    fun uploadStep(): Step = stepBuilderFactory.get("UPLOAD_JSON")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting UPLOAD_JSON")
            val articles = repository.findAll()
            uploader.upload(articles)
            log.info("Completed UPLOAD_JSON")
            RepeatStatus.FINISHED
        }).build()

    /**
     * Publishes processed article IDs to Pub/Sub.
     */
    @Bean
    fun publishStep(): Step = stepBuilderFactory.get("PUBLISH_PUBSUB")
        .tasklet(Tasklet { _, _ ->
            log.info("Starting PUBLISH_PUBSUB")
            val ids = repository.findAll().mapNotNull { it.id }
            publisher.publish(ids)
            log.info("Completed PUBLISH_PUBSUB")
            RepeatStatus.FINISHED
        }).build()
}
