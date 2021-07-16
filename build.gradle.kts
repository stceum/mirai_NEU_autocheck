import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    val kotlinVersion = "1.4.31"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.5-M1"
}

group = "me.stceum"
version = "0.1.0"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenCentral()
    jcenter()
    flatDir{ dirs ("./src/lib")}
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))
}