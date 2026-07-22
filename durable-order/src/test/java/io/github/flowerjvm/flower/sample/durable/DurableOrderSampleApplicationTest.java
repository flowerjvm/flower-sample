package io.github.flowerjvm.flower.sample.durable;

import io.github.flowerjvm.flower.sample.durable.domain.OrderRecord;
import io.github.flowerjvm.flower.sample.durable.domain.OrderRepository;
import io.github.flowerjvm.flower.sample.durable.domain.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:sqlite:file:durable-order-test?mode=memory&cache=shared&busy_timeout=5000",
                "spring.datasource.driver-class-name=org.sqlite.JDBC",
                "spring.datasource.hikari.maximum-pool-size=1",
                "flower.persistence.jdbc.dialect=sqlite"
        })
class DurableOrderSampleApplicationTest {

    @Autowired
    TestRestTemplate rest;

    @Autowired
    OrderRepository orders;

    @BeforeEach
    void reset() {
        rest.postForEntity("/api/reset", null, Map.class);
    }

    @Test
    void durableOrderStoresCheckpointAndCompletesAfterPayment() throws Exception {
        rest.postForEntity("/api/orders", Map.of("orderId", "ORDER-RECOVER"), Map.class);

        awaitStatus("ORDER-RECOVER", OrderStatus.WAITING_PAYMENT);

        ResponseEntity<Map> waiting = rest.getForEntity("/api/state", Map.class);
        assertThat(waiting.getBody()).isNotNull();
        assertThat(waiting.getBody().get("checkpoints").toString()).contains("wait-payment");

        rest.postForEntity("/api/orders/ORDER-RECOVER/pay", null, Map.class);
        awaitStatus("ORDER-RECOVER", OrderStatus.COMPLETED);
        awaitCheckpointMissing("ORDER-RECOVER");

        ResponseEntity<Map> completed = rest.getForEntity("/api/state", Map.class);
        assertThat(completed.getBody()).isNotNull();
        assertThat(completed.getBody().get("checkpoints").toString()).doesNotContain("ORDER-RECOVER");
    }

    @Test
    void transientAuditDoesNotCreateCheckpoint() throws Exception {
        rest.postForEntity("/api/audits", Map.of("message", "test transient audit"), Map.class);
        Thread.sleep(300L);

        ResponseEntity<Map> state = rest.getForEntity("/api/state", Map.class);
        assertThat(state.getBody()).isNotNull();
        assertThat(state.getBody().get("checkpoints").toString()).doesNotContain("audit");
    }

    @Test
    void restartingSameDurableStepDoesNotResetItsPersistedTimer() {
        orders.createOrder("ORDER-TIMER");
        orders.startStepTimer("ORDER-TIMER", "validate-order", 1_000L);
        orders.startStepTimer("ORDER-TIMER", "validate-order", 5_000L);

        assertThat(orders.stepTimerElapsed("ORDER-TIMER", "validate-order", 6_000L, 5_000L))
                .isTrue();
    }

    private void awaitStatus(String orderId, OrderStatus status) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 60_000L;
        while (System.currentTimeMillis() < deadline) {
            OrderRecord order = orders.find(orderId);
            if (order != null && order.getStatus() == status) {
                return;
            }
            Thread.sleep(100L);
        }
        throw new AssertionError("order did not reach " + status + ": " + orderId);
    }

    private void awaitCheckpointMissing(String orderId) throws InterruptedException {
        long deadline = System.currentTimeMillis() + 60_000L;
        while (System.currentTimeMillis() < deadline) {
            ResponseEntity<Map> state = rest.getForEntity("/api/state", Map.class);
            if (state.getBody() != null
                    && !state.getBody().get("checkpoints").toString().contains(orderId)) {
                return;
            }
            Thread.sleep(100L);
        }
        throw new AssertionError("checkpoint was not removed: " + orderId);
    }
}
