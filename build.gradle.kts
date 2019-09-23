import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
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
    id("org.flywaydb.flyway") version "6.0.3"
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
    plugin("org.flywaydb.flyway")
}

application {
    mainClassName = "io.github.emikaelsilveira.application.KotlinFullSampleApplication"
}

val sourceSets = the<SourceSetContainer>()

sourceSets {
    create("integrationTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/integrationTest/kotlin")
            resources.srcDir("src/integrationTest/resources")
            compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

tasks.test {
    useJUnitPlatform() {
        includeEngines("spek")
    }
}

task<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    mustRunAfter(tasks["test"])

    useJUnitPlatform() {
        includeEngines("spek")
    }
}

tasks.named("test") {
    finalizedBy("integrationTest")
}

tasks.jar {
    manifest {
        attributes(
            mapOf("Main-Class" to application.mainClassName)
        )
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
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
    implementation("org.flywaydb:flyway-core:6.0.3")
    implementation("org.json:json:20190722")

    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.assertj:assertj-core:3.11.1")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5")
    testImplementation("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testImplementation("com.opentable.components:otj-pg-embedded:0.13.1")

    integrationTestImplementation("org.assertj:assertj-core:3.11.1")
    integrationTestImplementation("org.jetbrains.spek:spek-api:1.1.5")
    integrationTestImplementation("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    integrationTestImplementation("com.opentable.components:otj-pg-embedded:0.13.1")
    integrationTestImplementation("org.junit.platform:junit-platform-engine:1.5.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

flyway {
    url = "jdbc:postgresql://localhost:5432/kotlinfullsample"
    user = "postgres"
    password = "postgres"
}
