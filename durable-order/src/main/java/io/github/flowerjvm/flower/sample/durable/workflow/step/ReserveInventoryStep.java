package io.github.flowerjvm.flower.sample.durable.workflow.step;

import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;

public final class ReserveInventoryStep extends TimedOrderStep {

    public ReserveInventoryStep(OrderRepository orders, String orderId) {
        super(orders, orderId);
    }

    @Override
    protected void apply() {
        orders.reserveInventory(orderId);
    }
}
