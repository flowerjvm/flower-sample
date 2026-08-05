plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation("io.github.flowerjvm:flower-spring-boot-starter:0.1.2")
    implementation("io.github.flowerjvm:flower-persistence-jdbc:0.1.2")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    runtimeOnly("org.xerial:sqlite-jdbc:3.46.1.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
