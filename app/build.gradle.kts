plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass = "orderandchaos.GUIMain"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

// Task for running in console mode
tasks.register<JavaExec>("runConsole") {
    setMain("orderandchaos.ConsoleMain")
    standardInput = System.`in`
    classpath = sourceSets.main.get().runtimeClasspath
}

// Task for running in GUI mode
tasks.register<JavaExec>("runGUI") {
    setMain("orderandchaos.GUIMain")
    classpath = sourceSets.main.get().runtimeClasspath
}