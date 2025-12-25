package pawg.hexagonal.cdc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@Profile({"cdc"})
public class CdcConfiguration {

    @Bean
    public io.debezium.config.Configuration customerConnector(@Value("${datasource.hostname}") String customerDbHost,
                                                              @Value("${datasource.port}") String customerDbPort,
                                                              @Value("${datasource.username}") String customerDbUsername,
                                                              @Value("${datasource.password}") String customerDbPassword,
                                                              @Value("${datasource.dbName}") String customerDbName) {

        System.out.println("Łączę jako: " + customerDbUsername + " do " + customerDbHost);

        return io.debezium.config.Configuration.create()
                .with("name", "backend-mysql-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")

                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "./offsets.dat")
                .with("offset.flush.interval.ms", "60000")

                .with("driver.serverTimezone", "UTC")
                .with("driver.connectionTimeZone", "UTC")
                .with("database.hostname", customerDbHost)
                .with("database.port", customerDbPort)
                .with("database.user", customerDbUsername)
                .with("database.password", customerDbPassword)
                .with("database.dbname", customerDbName)
                .with("database.server.id", "10181")
//                .with("database.server.name", "backend-mysql-db-server")
//                .with("database.include.list", customerDbName)
                .with("database.exclude.list", customerDbName)
//                .with("include.schema.changes", "false")

                .with("topic.prefix", "backend-cdc")

                .with("schema.history.internal", "io.debezium.storage.file.history.FileSchemaHistory")
                .with("schema.history.internal.file.filename", "./schemahistory.dat")

                .with("snapshot.mode", "initial")
                .with("snapshot.locking.mode", "none")
                .build();
    }
}
