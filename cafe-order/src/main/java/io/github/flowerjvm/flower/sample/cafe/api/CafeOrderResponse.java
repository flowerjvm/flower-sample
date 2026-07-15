package io.github.flowerjvm.flower.sample.cafe.api;

import io.github.flowerjvm.flower.sample.cafe.domain.CafeOrder;
import io.github.flowerjvm.flower.sample.cafe.domain.CafeOrderStatus;

import java.time.Instant;

public record CafeOrderResponse(String orderId, CafeOrderStatus status, Instant updatedAt) {

    public static CafeOrderResponse of(CafeOrder order) {
        return new CafeOrderResponse(order.orderId(), order.status(), order.updatedAt());
    }
}
