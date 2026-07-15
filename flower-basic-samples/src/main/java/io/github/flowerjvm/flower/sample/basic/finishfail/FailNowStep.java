package io.github.flowerjvm.flower.sample.basic.finishfail;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

final class FailNowStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        ConsoleFlowerListener.log("fail-now returns FAIL");
        return StepResult.fail(new IllegalStateException("sample failure"));
    }
}
