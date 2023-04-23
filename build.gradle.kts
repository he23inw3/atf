import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import java.lang.Thread.sleep

plugins {
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	id("org.openapi.generator") version "6.4.0"
  id("com.avast.gradle.docker-compose") version "0.14.0"
  id("io.gitlab.arturbosch.detekt") version "1.17.0"
  jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("com.mysql:mysql-connector-j")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.mockk:mockk:1.13.4")
  testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.1")
}

detekt {
    toolVersion = "1.17.0"
    config = files("./detekt.yml")
    buildUponDefaultConfig = true
}

dockerCompose {
    useComposeFiles = listOf("./docker-compose.yml")
    isRequiredBy(project.tasks.test)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
  useJUnitPlatform()
  jvmArgs("--add-opens", "java.base/java.time=ALL-UNNAMED")

  doFirst {
    sleep(30000)
  }
}

tasks.jacocoTestReport {
  executionData(files("$buildDir/jacoco/test.exec", "$buildDir/results/jacoco/cucumber.exec"))
  reports {
    xml.required.set(true)
    html.required.set(true)
  }
}

task<GenerateTask>("generateApiDoc") {
    generatorName.set("html2")
    inputSpec.set("$projectDir/config/swagger/openapi.yml")
    outputDir.set("$buildDir/openapi/doc/")
}
