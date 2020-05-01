plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    `java-library`
}

group = "com.github.pgreze"
version = properties.getOrDefault("VERSION", "WIP").toString()

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
