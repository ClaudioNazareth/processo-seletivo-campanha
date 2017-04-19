package br.com.campanha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "br.com.campanha.repository")
public class ApplicationStarter {

    public static void main(String[] args) throws Exception {
        ApplicationContext app = SpringApplication.run(ApplicationStarter.class, args);
    }

}
