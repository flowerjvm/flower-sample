package io.github.flowerjvm.flower.sample.basic.guard;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

final class GuardedStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        ConsoleFlowerListener.log("guarded-step runs after guard pass");
        return StepResult.done();
    }
}
