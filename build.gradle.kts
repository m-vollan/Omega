import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}
plugins {
  java
  idea
  kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    //id "com.github.ben-manes.versions" version "0.20.0"
    //id 'com.sedmelluq.jdaction' version '1.0.2'
}
group = "io.github.m_vollan"

repositories {
  //jcenter()
  mavenCentral()
  maven("https://m2.dv8tion.net/releases")
}

//val compile by configurations.creating

// In this section you declare the dependencies for your production and test code
dependencies {
  implementation (group= "org.jetbrains.kotlin", name= "kotlin-stdlib", version="1.7.10")
  implementation (group= "org.jetbrains.kotlinx", name= "kotlinx-coroutines-core", version="1.6.3")
  implementation (group= "org.json", name= "json", version="20220320")
  implementation (group= "ch.qos.logback", name= "logback-classic", version="1.2.11")
  implementation (group = "net.dv8tion", name= "JDA", version = "5.0.0-alpha.18")
  implementation (group = "club.minnced", name= "discord-webhooks", version= "0.8.2")
   // implementation(group = "commons-io", name= "commons-io", version= "2.11.0")
//implementation(kotlin("stdlib-jdk8"))
}

sourceSets["main"].withConvention(conventionType = org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class){
    kotlin.srcDir("$projectDir/src/kotlin")
}

tasks {
    withType<KotlinCompile> {
        (kotlinOptions).apply {
            jvmTarget = JavaVersion.VERSION_16.toString()
        }
    }
    jar{
        manifest.attributes["Main-Class"] = "io.github.m_vollan.omega.Main"
    }
    "build" {
        dependsOn(fatJar)
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_16.toString()
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = JavaVersion.VERSION_16.toString()
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = "1.0"
        attributes["Main-Class"] = "io.github.m_vollan.omega.Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}