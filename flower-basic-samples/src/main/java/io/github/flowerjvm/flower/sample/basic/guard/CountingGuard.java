package io.github.flowerjvm.flower.sample.basic.guard;

import io.github.flowerjvm.flower.core.step.Guard;
import io.github.flowerjvm.flower.core.step.GuardResult;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

public final class CountingGuard implements Guard {

    private final int passAfterChecks;
    private int checks;

    public CountingGuard(int passAfterChecks) {
        if (passAfterChecks < 1) {
            throw new IllegalArgumentException("passAfterChecks must be positive");
        }
        this.passAfterChecks = passAfterChecks;
    }

    @Override
    public GuardResult check(StepContext ctx) {
        checks++;
        if (checks < passAfterChecks) {
            ConsoleFlowerListener.log("guard holds " + ctx.currentStepId() + " check " + checks);
            return GuardResult.hold();
        }
        ConsoleFlowerListener.log("guard passes " + ctx.currentStepId() + " check " + checks);
        return GuardResult.pass();
    }

    public int checks() {
        return checks;
    }
}
