plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    // Lint
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    // Deploy
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
}

group = "com.github.pgreze"
version = System.getenv("GITHUB_REF")?.split('/')?.last()?.trimStart('v') ?: "WIP"
description = "Counting easily with Kotlin"
val github = "https://github.com/pgreze/kounter"

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform {
        includeEngines("spek2")
    }
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
