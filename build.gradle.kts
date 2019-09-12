import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", "1.3.30"))
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
    }
}

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "io.emikaelsilveira"
version = "1.0.0"

repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://kotlin.bintray.com/kotlinx")
    maven(url = "https://dl.bintray.com/spekframework/spek-dev")
}

apply {
    plugin("org.junit.platform.gradle.plugin")
}

application {
    mainClassName = "io.github.emikaelsilveira.application.KotlinFullSampleApplication"
}

tasks.withType<Test> {
    useJUnitPlatform() {
        includeEngines("spek")
    }
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf("Main-Class" to application.mainClassName)
        )
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.javalin:javalin:3.4.1")
    implementation("org.jetbrains.exposed:exposed:0.16.3")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.9.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.9")
    implementation("org.koin:koin-core:2.0.1")
    implementation("com.zaxxer:HikariCP:3.3.1")
    implementation("org.slf4j:slf4j-simple:1.7.28")
    implementation("org.postgresql:postgresql:42.2.5")
    implementation("com.github.kittinunf.fuel:fuel:2.2.0")

    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5")
    testImplementation("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
