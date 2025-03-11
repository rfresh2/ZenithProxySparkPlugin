plugins {
    id("zenithproxy.plugin.dev") version "1.0.0-SNAPSHOT"
}

group = properties["maven_group"] as String
version = properties["plugin_version"] as String

java { toolchain { languageVersion = JavaLanguageVersion.of(23) } }

zenithProxy {
    mc = properties["mc"] as String
    templateProperties = mapOf(
        "version" to project.version
    )
}

repositories {
    mavenLocal()
    maven("https://maven.2b2t.vc/releases")
    maven("https://repo.lucko.me")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    shade("me.lucko:spark-common:1.10.119-SNAPSHOT")
}
