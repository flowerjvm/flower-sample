package io.github.flowerjvm.flower.sample.basic.guard;

import io.github.flowerjvm.flower.core.flow.Flow;
import io.github.flowerjvm.flower.sample.basic.support.SampleRuntime;

public final class GuardFlowSample {

    private GuardFlowSample() {
    }

    public static Flow createFlow(String flowKey, int passAfterChecks) {
        return createFlow(flowKey, new CountingGuard(passAfterChecks));
    }

    public static Flow createFlow(String flowKey, CountingGuard guard) {
        return Flow.builder("basic-guard", flowKey)
                .step("guarded-step", new GuardedStep(), guard)
                .step("after-guard", new AfterGuardStep())
                .build();
    }

    public static void main(String[] args) throws Exception {
        SampleRuntime.runToTerminal(createFlow("console", 4), 10L);
    }
}
