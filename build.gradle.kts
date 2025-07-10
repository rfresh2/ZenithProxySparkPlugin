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
    maven("https://maven.2b2t.vc/releases") {
        description = "ZenithProxy Releases"
    }
    maven("https://maven.2b2t.vc/remote") {
        description = "Dependencies used by ZenithProxy"
    }
    maven("https://repo.lucko.me")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    zenithProxy("com.zenith:ZenithProxy:$mc-SNAPSHOT")
    shade("me.lucko:spark-common:1.10.134-SNAPSHOT")
}

tasks {
    shadowJar {
        relocate("net.bytebuddy", "dev.zenith.spark.shadow.bytebuddy")
        relocate("org.objectweb.asm", "dev.zenith.spark.shadow.asm")
        relocate("org.java_websocket", "dev.zenith.spark.shadow.java_websocket")
        relocate("com.google.protobuf", "dev.zenith.spark.shadow.protobuf")
        dependencies {
            exclude(dependency("net.kyori:adventure-api:.*"))
            exclude(dependency("net.kyori:adventure-key:.*"))
            exclude(dependency("net.kyori:adventure-nbt:.*"))
            exclude(dependency("net.kyori:adventure-text-logger-slf4j:.*"))
            exclude(dependency("net.kyori:adventure-text-minimessage:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-ansi:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-commons:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-gson:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-json:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-json-legacy-impl:.*"))
            exclude(dependency("net.kyori:adventure-text-serializer-legacy:.*"))
            exclude(dependency("net.kyori:ansi:.*"))
            exclude(dependency("net.kyori:examination-api:.*"))
            exclude(dependency("net.kyori:examination-string:.*"))
            exclude(dependency("net.kyori:option:.*"))
        }
    }
}
