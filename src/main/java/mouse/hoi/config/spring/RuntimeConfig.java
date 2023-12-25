package mouse.hoi.config.spring;

import mouse.hoi.config.loader.ConfigDefaultLoader;
import mouse.hoi.config.loader.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppConfig.class)
public class RuntimeConfig {

    @Bean
    public ConfigLoader getLoader() {
        return new ConfigDefaultLoader();
    }
}
