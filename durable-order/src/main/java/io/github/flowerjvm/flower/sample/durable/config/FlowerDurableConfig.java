package io.github.flowerjvm.flower.sample.durable.config;

import io.github.flowerjvm.flower.core.listener.FlowerListener;
import io.github.flowerjvm.flower.observability.logging.LoggingFlowerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowerDurableConfig {

    @Bean
    public FlowerListener flowerLoggingListener() {
        return LoggingFlowerListener.builder().build();
    }
}
