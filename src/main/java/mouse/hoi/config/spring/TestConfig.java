package mouse.hoi.config.spring;

import mouse.hoi.config.loader.ConfigLoader;
import mouse.hoi.config.loader.ConfigTestsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppConfig.class)
public class TestConfig {
    @Bean
    public ConfigLoader getLoader() {
        return new ConfigTestsLoader();
    }
}
