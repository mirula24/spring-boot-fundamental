package simpleCRUD.unitTest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfig {
    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }
}
