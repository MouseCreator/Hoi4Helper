package mouse.hoi.config;

import mouse.hoi.config.spring.RuntimeConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UtilInvoke {
    public static GeneralUtils generalUtils() {
        return new AnnotationConfigApplicationContext(RuntimeConfig.class).getBean(GeneralUtils.class);
    }
}
