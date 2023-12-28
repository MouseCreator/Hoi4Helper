package mouse.hoi.config;

import mouse.hoi.config.spring.RuntimeConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UtilInvoke {
    public static GeneralUtils generalUtils() {
        return context().getBean(GeneralUtils.class);
    }

    public static ApplicationContext context() {
        return new AnnotationConfigApplicationContext(RuntimeConfig.class);
    }
}
