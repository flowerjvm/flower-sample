package io.github.flowerjvm.flower.sample.battle.config;

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
 * Wires Bloom and Flower together for the game-turn-battle sample.
 *
 * <p>Same shape as the other samples: two {@code EventBus} beans, one for
 * the Bloom side that REST endpoints publish into, one wrapping it for
 * Flower so Step subscriptions see the same events.
 */
@Configuration
public class FlowerBattleConfig {

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
