plugins {
    application
}

dependencies {
    implementation("io.github.flowerjvm:flower-core:0.1.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.3")
}

application {
    mainClass.set("io.github.flowerjvm.flower.sample.basic.done.DoneFlowSample")
}

fun registerSampleTask(taskName: String, mainClassName: String) {
    tasks.register<JavaExec>(taskName) {
        group = "flower samples"
        description = "Run $mainClassName"
        classpath = sourceSets.main.get().runtimeClasspath
        mainClass.set(mainClassName)
    }
}

registerSampleTask(
    "runDoneFlowSample",
    "io.github.flowerjvm.flower.sample.basic.done.DoneFlowSample"
)
registerSampleTask(
    "runStayFlowSample",
    "io.github.flowerjvm.flower.sample.basic.stay.StayFlowSample"
)
registerSampleTask(
    "runGoToFlowSample",
    "io.github.flowerjvm.flower.sample.basic.gotoexample.GoToFlowSample"
)
registerSampleTask(
    "runEventFlowSample",
    "io.github.flowerjvm.flower.sample.basic.event.EventFlowSample"
)
registerSampleTask(
    "runRepeatFlowSample",
    "io.github.flowerjvm.flower.sample.basic.repeat.RepeatFlowSample"
)
registerSampleTask(
    "runFinishFlowSample",
    "io.github.flowerjvm.flower.sample.basic.finishfail.FinishFlowSample"
)
registerSampleTask(
    "runFailFlowSample",
    "io.github.flowerjvm.flower.sample.basic.finishfail.FailFlowSample"
)
registerSampleTask(
    "runGuardFlowSample",
    "io.github.flowerjvm.flower.sample.basic.guard.GuardFlowSample"
)
