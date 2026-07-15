package io.github.flowerjvm.flower.sample.basic.stay;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.basic.support.ConsoleFlowerListener;

public final class StayThenDoneStep extends Step {

    private static final int START = 0;
    private static final int WAITING = 10;

    private final long delayMillis;

    public StayThenDoneStep(long delayMillis) {
        this.delayMillis = delayMillis;
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        switch (ctx.stepNo()) {
            case START:
                ConsoleFlowerListener.log("StayThenDone stepNo=0, start timeout");
                ctx.startTimeout(delayMillis);
                ctx.setStepNo(WAITING);
                return StepResult.stay();

            case WAITING:
                if (ctx.timedOut()) {
                    ConsoleFlowerListener.log("StayThenDone stepNo=10, timeout reached, done");
                    return StepResult.done();
                }
                return StepResult.stay();

            default:
                return StepResult.fail(new IllegalStateException("unknown stepNo: " + ctx.stepNo()));
        }
    }
}
