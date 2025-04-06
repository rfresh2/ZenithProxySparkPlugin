plugins {
    id("zenithproxy.plugin.dev") version "1.0.0-SNAPSHOT"
}

group = properties["maven_group"] as String
version = properties["plugin_version"] as String
val mc = properties["mc"] as String

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

zenithProxyPlugin {
    templateProperties = mapOf(
        "version" to project.version
    )
}

repositories {
    mavenLocal() // if developing against a locally published zenith build
    maven("https://maven.2b2t.vc/remote") {
        description = "Dependencies used by ZenithProxy"
    }
    maven("https://maven.2b2t.vc/releases") {
        description = "ZenithProxy Releases"
    }
    maven("https://repo.lucko.me")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    zenithProxy("com.zenith:ZenithProxy:$mc-SNAPSHOT")
    shade("me.lucko:spark-common:1.10.119-SNAPSHOT")
}
