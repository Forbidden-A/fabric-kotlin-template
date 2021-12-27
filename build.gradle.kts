
/* val Project.libs: LibrariesForLibs' can't be called in this context by implicit receiver. Use the explicit one if necessary */
/* https://youtrack.jetbrains.com/issue/KTIJ-19369 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    java
    `maven-publish`
    alias(libs.plugins.kotlin)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.fabric.loom)
}

group      = "dev.forb"
version    = "1.0-SNAPSHOT"

repositories {

}

dependencies {
    minecraft(libs.minecraft)
    mappings("net.fabricmc:yarn:1.18.1+build.12:v2")
    modImplementation(libs.fabric.loader)

    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.fabric.api)
}

tasks {

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(remapJar) {
                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {
                    builtBy(remapSourcesJar)
                }
            }
        }

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            // mavenLocal()
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}

java {
    withSourcesJar()
}
