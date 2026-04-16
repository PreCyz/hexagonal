package pawg.hexagonal.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {

    static void main(String[] args) {
//        System.setProperty("org.slf4j.simpleLogger.log.org.openjproxy.grpc.client", "debug");
        SpringApplication.run(AppApplication.class, args);
    }

}
