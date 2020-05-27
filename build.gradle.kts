plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    // Lint
    jacoco
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    // Deploy
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "com.github.pgreze"
val tagVersion = System.getenv("GITHUB_REF")?.split('/')?.last()
version = tagVersion?.trimStart('v') ?: "WIP"
description = "Counting easily with Kotlin"
val github = "https://github.com/pgreze/kounter"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
    baseline = file("detekt-baseline.xml")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
    }
    finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = System.getenv("CI") != "true"
    }
}

val moveCss by tasks.registering {
    description = "Move style.css in kounter folder, easier for distribution."
    fun File.rewriteStyleLocations() {
        readText().replace("../style.css", "style.css")
            .also { writeText(it) }
    }
    fun File.recursivelyRewriteStyleLocations() {
        list()?.map(this::resolve)?.forEach {
            if (it.isDirectory) it.recursivelyRewriteStyleLocations() else it.rewriteStyleLocations()
        }
    }
    doLast {
        val dokkaOutputDirectory = file(tasks.dokka.get().outputDirectory)
        val kounterFolder = dokkaOutputDirectory.resolve("kounter")
        kounterFolder.recursivelyRewriteStyleLocations()
        dokkaOutputDirectory.resolve("style.css").also {
            it.renameTo(kounterFolder.resolve(it.name))
        }
    }
}
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/dokka"
    configuration {
        sourceLink {
            // URL showing where the source code can be accessed through the web browser
            url = "https://github.com/pgreze/kounter/tree/${tagVersion ?: "master"}/"
            // Suffix which is used to append the line number to the URL. Use #L for GitHub
            lineSuffix = "#L"
        }
    }
    finalizedBy(moveCss)
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.amshove.kluent:kluent:1.61")
    val junit5 = "5.3.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junit5")
    val spek = "2.0.7"
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek")
    testImplementation(kotlin("reflect"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                url.set(github)
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    connection.set("$github.git")
                    developerConnection.set("scm:git:ssh://github.com:pgreze/kounter.git")
                    url.set(github)
                }
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_API_KEY")

    setPublications("maven")

    pkg.apply {
        repo = "maven"
        name = project.name
        desc = project.description
        websiteUrl = github
        vcsUrl = "$github.git"
        setLicenses("Apache-2.0")
        publish = true
        publicDownloadNumbers = true
        version.apply {
            desc = project.description
            gpg.apply {
                // Determines whether to GPG sign the files (default: false)
                sign = true
                // The passphrase for GPG signing (optional)
                passphrase = System.getenv("BINTRAY_GPG_PASSWORD")
            }
        }
    }
}
