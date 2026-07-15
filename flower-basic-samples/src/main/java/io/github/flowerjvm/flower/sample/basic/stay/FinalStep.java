package io.github.flowerjvm.flower.sample.basic.stay;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

public final class FinalStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        ConsoleFlowerListener.log("FinalStep Done!");
        return StepResult.done();
    }
}
