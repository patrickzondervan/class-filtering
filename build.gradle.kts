plugins {
    kotlin("jvm") version "1.6.20"
}

group = "io.github.patrickzondervan.finder"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}