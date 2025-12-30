package pawg.hexagonal.cdc.config;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.engine.format.KeyValueChangeEventFormat;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pawg.hexagonal.cdc.services.DebeziumService;

@Configuration
@EnableConfigurationProperties
public class CdcConfiguration {
    private final Map<String, String> debeziumConfiguration = new HashMap<>();

    @Bean(name = "debeziumConfiguration")
    @ConfigurationProperties(prefix = "debezium.configuration")
    public Map<String, String> debeziumConfiguration() {
        return debeziumConfiguration;
    }

    @Bean
    public io.debezium.config.Configuration debeziumConnector(final Map<String, String> debeziumConfiguration) {
        return io.debezium.config.Configuration.create().apply((b) -> debeziumConfiguration.forEach(b::with)).build();
    }

    @Bean
    public DebeziumEngine<ChangeEvent<String, String>> debeziumEngine(io.debezium.config.Configuration debeziumConnector, DebeziumService debeziumService) {
        return DebeziumEngine.create(KeyValueChangeEventFormat.of(Json.class, Json.class))
                                       .using(debeziumConnector.asProperties())
                                       .notifying(debeziumService::handleChangeEvent)
                                       .build();
    };
}
