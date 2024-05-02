plugins {
    java
    application
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {
    // Suppressions for SpotBugs
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.4")

    // Maven dependencies are composed by a group name, a name and a version, separated by colons
    implementation("com.omertron:API-OMDB:1.5")
    implementation("org.jooq:jool:0.9.14")
    implementation("org.slf4j:slf4j-api:1.7.36")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.6")
}

application {
    mainClass.set("it.unibo.sampleapp.RateAMovie")
}
