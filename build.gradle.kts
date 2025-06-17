plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.cloud:spring-cloud-starter-task")
    implementation("org.jsoup:jsoup:1.17.1")
    implementation("com.google.cloud:google-cloud-storage:2.23.0")
    implementation("com.google.cloud:google-cloud-pubsub:1.124.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.testcontainers:junit-jupiter:1.19.0")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.testcontainers:postgresql:1.19.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
