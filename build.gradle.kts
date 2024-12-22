plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.4"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "sh.rime.demo"
version = "0.0.1-SNAPSHOT"

val shoreVersion = "2.2.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("sh.rime.reactor:shore-r2dbc")
    implementation("sh.rime.reactor:shore-log")
    implementation("sh.rime.reactor:shore-web")
    implementation("sh.rime.reactor:shore-rabbitmq")
    implementation("sh.rime.reactor:shore-security")
    implementation("sh.rime.reactor:shore-redis")
    testImplementation("sh.rime.reactor:shore-test")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks.withType<Test> {
    finalizedBy(tasks.named("jacocoTestReport"))
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        events("passed", "skipped", "failed")
    }
    systemProperty("spring.profiles.active", "test")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

dependencyManagement {
    imports {
        mavenBom("sh.rime.reactor:shore-bom:$shoreVersion")
    }
    resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
    }
}

jib {
    setAllowInsecureRegistries(true)
    from {
        image = "youtaqiu/jre-otel:21"
    }
    to {
        image = "youtaqiu/${project.name}"
        tags = setOf("latest")
    }
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
        jvmFlags = listOf(
            "-Djava.security.egd=file:/dev/./urandom",
            "-Dfile.encoding=utf-8",
            "-Dnacos.logging.default.config.enabled=false",
            "-Duser.timezone=GMT+08",
            "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
            "-Xshare:off",
            "-javaagent:/app/lib/opentelemetry.jar"
        )
    }
}


tasks.bootBuildImage {
    imageName.set("docker.io/library/shore-demo:0.0.1")
}
