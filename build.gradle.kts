plugins {
    id("net.fabricmc.fabric-loom-remap")
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "2.3.21"
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "SpongePowered"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    mavenCentral()
    flatDir { dirs("libs") }
}

loom {
    splitEnvironmentSourceSets()
    mods {
        register("nextgenoptimizer") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
    mappings("net.fabricmc:yarn:${providers.gradleProperty("yarn_mappings").get()}:v2")
    modImplementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks.processResources {
    inputs.property("version", version)
    filesMatching("fabric.mod.json") { expand("version" to version) }
}

tasks.withType<JavaCompile>().configureEach { options.release = 21 }

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
