package pawg.hexagonal.cdc.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

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
    public io.debezium.config.Configuration customerConnector(final Map<String, String> debeziumConfiguration) {
        return io.debezium.config.Configuration.create().apply((b) -> debeziumConfiguration.forEach(b::with)).build();
    }
}
