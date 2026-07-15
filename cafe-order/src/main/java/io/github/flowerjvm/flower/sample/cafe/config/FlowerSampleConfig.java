package io.github.flowerjvm.flower.sample.cafe.config;

import io.github.flowerjvm.bloom.LocalEventBus;
import io.github.flowerjvm.bloom.flower.BloomEventBus;
import io.github.flowerjvm.flower.core.event.EventBus;
import io.github.flowerjvm.flower.core.listener.FlowerListener;
import io.github.flowerjvm.flower.observability.logging.LoggingFlowerListener;
import io.github.flowerjvm.flower.observability.metrics.MicrometerFlowerListener;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Wires Bloom and Flower together for the sample.
 *
 * <p>Two distinct {@code EventBus} types live in this app:
 * <ul>
 *   <li>{@code io.github.flowerjvm.bloom.EventBus} - the Bloom native bus
 *       used by sample partner classes (and any non-Flower code) to publish
 *       events.</li>
 *   <li>{@code io.github.flowerjvm.flower.core.event.EventBus} - what
 *       Flower core understands. {@link BloomEventBus#wrap(io.github.flowerjvm.bloom.EventBus)}
 *       turns the Bloom bus into one of these.</li>
 * </ul>
 *
 * <p>The flower-spring-boot-starter sees the {@code EventBus} bean below and
 * uses it instead of its default in-memory bus, so every Step ends up
 * publishing/subscribing through Bloom.
 */
@Configuration
public class FlowerSampleConfig {

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

    @Bean
    public TaskScheduler cafePartnerScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("cafe-partner-");
        scheduler.setDaemon(true);
        return scheduler;
    }
}
