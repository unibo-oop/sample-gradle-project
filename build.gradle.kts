plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application
    
    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.diffplug.durian:durian:2.0") // one-line lambda exception handling
    implementation("com.google.apis:google-api-services-books:v1-rev20201021-1.30.10")
    implementation("com.omertron:API-OMDB:1.5")

    runtimeOnly("org.slf4j:slf4j-log4j12:1.7.30")

    // JUnit API and testing engine
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    // Enables JUnit 5 Jupiter module
    useJUnitPlatform()
}

application {
    // Define the main class for the application.
    mainClassName = "it.unibo.sampleapp.App"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.register("listPlugins") {
    doLast {
        project.plugins.forEach {
            println(it)
        }
    }
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "it.unibo.sampleapp.App"
    }
}

task("runMain", JavaExec::class) {
    main = "it.unibo.sampleapp.App"
    classpath = sourceSets["main"].runtimeClasspath
}
