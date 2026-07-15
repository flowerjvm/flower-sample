package io.github.flowerjvm.flower.sample.logistics.config;

import io.github.flowerjvm.bloom.LocalEventBus;
import io.github.flowerjvm.bloom.flower.BloomEventBus;
import io.github.flowerjvm.flower.core.event.EventBus;
import io.github.flowerjvm.flower.core.listener.FlowerListener;
import io.github.flowerjvm.flower.observability.logging.LoggingFlowerListener;
import io.github.flowerjvm.flower.observability.metrics.MicrometerFlowerListener;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wires Bloom and Flower together for the logistics-control sample.
 *
 * <p>This sample is now self-driving: once a work order enters Flower, the
 * Zone Steps use shared robot capacity and timeouts instead of external
 * button events. The Bloom adapter is still wired so the sample collection
 * keeps the same Flower event-bus integration shape.
 */
@Configuration
public class FlowerLogisticsConfig {

    @Bean
    public io.github.flowerjvm.bloom.EventBus bloomEventBus() {
        return LocalEventBus.create();
    }

    @Bean
    public EventBus flowerEventBus(io.github.flowerjvm.bloom.EventBus bloom) {
        return BloomEventBus.wrap(bloom);
    }

    @Bean
    public FlowerListener flowerLoggingListener() {
        return LoggingFlowerListener.builder().build();
    }

    @Bean
    public FlowerListener flowerMetricsListener(MeterRegistry registry) {
        return new MicrometerFlowerListener(registry);
    }
}
