package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.core.step.Step;
import io.github.flowerjvm.flower.core.step.StepContext;
import io.github.flowerjvm.flower.core.step.StepResult;
import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

public final class WaitPaymentStep extends Step {

    private static final int WAITING_PAYMENT = 0;
    private static final int PAYMENT_CONFIRMED = 10;
    private static final long CONFIRMATION_MILLIS = 5_000L;

    private final OrderRepository orders;
    private final String orderId;

    public WaitPaymentStep(OrderRepository orders, String orderId) {
        this.orders = orders;
        this.orderId = orderId;
    }

    @Override
    protected void onEnter(StepContext ctx) {
        orders.waitForPayment(orderId);
    }

    @Override
    protected StepResult onTick(StepContext ctx) {
        if (ctx.stepNo() == WAITING_PAYMENT) {
            if (!orders.isPaid(orderId)) {
                return StepResult.stay();
            }
            orders.startStepTimer(orderId, ctx.currentStepId(), ctx.clock().currentTimeMillis());
            ctx.setStepNo(PAYMENT_CONFIRMED);
            return StepResult.stay();
        }
        if (ctx.stepNo() == PAYMENT_CONFIRMED) {
            return orders.stepTimerElapsed(
                    orderId,
                    ctx.currentStepId(),
                    ctx.clock().currentTimeMillis(),
                    CONFIRMATION_MILLIS)
                    ? StepResult.done()
                    : StepResult.stay();
        }
        return StepResult.fail(new IllegalStateException("unknown payment stepNo: " + ctx.stepNo()));
    }
}
