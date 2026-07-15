package io.github.flowerjvm.flower.sample.basic.gotoexample;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

public final class TargetStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        ConsoleFlowerListener.log("TargetStep Done!");
        return StepResult.done();
    }
}
