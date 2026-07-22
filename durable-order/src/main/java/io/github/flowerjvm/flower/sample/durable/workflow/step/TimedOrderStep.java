package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

abstract class TimedOrderStep extends Step {

    private static final int START = 0;
    private static final int WAITING = 10;
    private static final long STEP_DELAY_MILLIS = 5_000L;

    protected final OrderRepository orders;
    protected final String orderId;

    TimedOrderStep(OrderRepository orders, String orderId) {
        this.orders = orders;
        this.orderId = orderId;
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        if (ctx.stepNo() == START) {
            orders.startStepTimer(orderId, ctx.currentStepId(), ctx.clock().currentTimeMillis());
            apply();
            ctx.setStepNo(WAITING);
            return StepResult.stay();
        }
        if (ctx.stepNo() == WAITING) {
            return orders.stepTimerElapsed(
                    orderId,
                    ctx.currentStepId(),
                    ctx.clock().currentTimeMillis(),
                    STEP_DELAY_MILLIS)
                    ? StepResult.done()
                    : StepResult.stay();
        }
        return StepResult.fail(new IllegalStateException("unknown order stepNo: " + ctx.stepNo()));
    }

    protected abstract void apply();
}
