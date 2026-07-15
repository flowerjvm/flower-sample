package io.github.flowerjvm.flower.sample.basic.finishfail;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;

final class ShouldNotRunStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        return StepResult.fail(new IllegalStateException("this step should not run"));
    }
}
