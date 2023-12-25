package mouse.hoi.config;

import mouse.hoi.config.spring.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;


class GeneralUtilsTest {

    @Test
    void getModData() {
        GeneralUtils generalUtils = new AnnotationConfigApplicationContext(TestConfig.class).getBean(GeneralUtils.class);
        String modLocation = generalUtils.getModData().getModLocation();
        assertEquals("Location", modLocation);
        System.out.println(modLocation);
    }
}