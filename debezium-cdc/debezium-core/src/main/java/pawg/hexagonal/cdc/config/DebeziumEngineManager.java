package pawg.hexagonal.cdc.config;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DependsOn("dataSourceScriptDatabaseInitializer")
public class DebeziumEngineManager implements SmartLifecycle {
    private final DebeziumEngine<ChangeEvent<String, String>> engine;
    private final ExecutorService executor;
    private volatile boolean running = false;
    private Future<?> task;

    @Autowired
    public DebeziumEngineManager(DebeziumEngine<ChangeEvent<String, String>> engine) {
        this.engine = engine;
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void start() {
        if (running) {
            return;
        }
        task = executor.submit(engine);
        running = true;
    }

    @Override
    public void stop() {
        try {
            engine.close();
        } catch (IOException e) {
            log.error("Could not stop debezium engine gracefully. {}", e.getMessage());
        } finally {
            if (task != null) {
                task.cancel(true);
            }
            executor.shutdownNow();
            running = false;
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return 1000;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }
}
