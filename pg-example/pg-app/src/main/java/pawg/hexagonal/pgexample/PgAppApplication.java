package pawg.hexagonal.pgexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PgAppApplication {

    static void main(String[] args) {
//        System.setProperty("org.slf4j.simpleLogger.log.org.openjproxy.grpc.client", "debug");
        SpringApplication.run(PgAppApplication.class, args);
    }

}
