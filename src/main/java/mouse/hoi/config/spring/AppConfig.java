package mouse.hoi.config.spring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.xml.parsers.SAXParserFactory;

@Configuration
@ComponentScan(basePackages = "mouse.hoi")
public class AppConfig {

    @Bean
    public SAXParserFactory saxParserFactory() {
        return SAXParserFactory.newInstance();
    }
}
