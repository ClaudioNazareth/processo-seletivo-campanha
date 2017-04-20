package br.com.campanha;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.init.AbstractRepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * @author : Claudio Nazareth  chtnazareth@gmail.com
 */

@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "br.com.campanha.repository")
public class ApplicationStarter {

    public static void main(String[] args) throws Exception {
        ApplicationContext app = SpringApplication.run(ApplicationStarter.class, args);
    }

    /**
     * Responsible for reading data file from resources (data.json)
     * transform in a Object and loading to MongoDB
     */
    @Bean
    public AbstractRepositoryPopulatorFactoryBean repositoryPopulator() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        Jackson2RepositoryPopulatorFactoryBean factoryBean = new Jackson2RepositoryPopulatorFactoryBean();
        factoryBean.setResources(new Resource[] { new ClassPathResource("data.json") });
        factoryBean.setMapper(mapper);

        return factoryBean;
    }

}
