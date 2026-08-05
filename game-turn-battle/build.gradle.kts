plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    // Flower starter pulls flower-core + spring-boot autoconfiguration.
    implementation("io.github.flowerjvm:flower-spring-boot-starter:0.1.2")
    // Adapter that makes a Bloom EventBus visible to Flower as a flower-core EventBus.
    implementation("io.github.flowerjvm:bloom-flower-adapter:0.1.0")
    // Optional: SLF4J / Micrometer listeners + StepLogger.
    implementation("io.github.flowerjvm:flower-observability:0.1.2")
    // Bloom event bus implementation we wrap.
    implementation("io.github.flowerjvm:bloom-core:0.1.0")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
