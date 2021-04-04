plugins {
    id("fabric-loom") version "0.6.1"
    id("org.cadixdev.licenser") version "0.5.1"
}

val modVersion: String by project
val minecraftVersion: String by project
val yarnMappings: String by project
val loaderVersion: String by project

group = "com.demonwav"
version = modVersion

val modInclude: Configuration by configurations.creating {
    exclude(group = "net.fabricmc.fabric-api")
}
configurations.modApi {
    extendsFrom(modInclude)
}
configurations.include {
    extendsFrom(modInclude)
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.25.1+build.416-1.16")

    modInclude("me.sargunvohra.mcmods:autoconfig1u:3.3.1")
    modInclude("me.shedaniel.cloth:config-2:4.8.3")
    modImplementation("io.github.prospector:modmenu:1.14.13+build.22")
}

java {
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

license {
    header = file("header.txt")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(8)
}

tasks.processResources {
    inputs.property("version", project.version)

    from(sourceSets.main.get().resources.srcDirs) {
        include("fabric.mod.json")
        expand("version" to project.version)
    }

    from(sourceSets.main.get().resources.srcDirs) {
        exclude("fabric.mod.json")
    }
}

tasks.jar {
    from("LICENSE", "COPYING.lesser")
}
