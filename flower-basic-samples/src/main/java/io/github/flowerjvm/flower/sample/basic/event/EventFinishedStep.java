package io.github.flowerjvm.flower.sample.basic.event;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

public final class EventFinishedStep extends Step {

    @Override
    protected StepResult onTick(StepContext ctx) {
        ConsoleFlowerListener.log("EventFinishedStep Done!");
        return StepResult.done();
    }
}
