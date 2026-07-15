package io.github.flowerjvm.flower.sample.basic.done;

public final class StepOne extends DelayedDoneStep {

    public StepOne(long delayMillis) {
        super("Step1", delayMillis);
    }
}
