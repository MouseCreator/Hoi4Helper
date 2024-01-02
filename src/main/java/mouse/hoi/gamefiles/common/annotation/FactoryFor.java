package mouse.hoi.gamefiles.common.annotation;

import mouse.hoi.gamefiles.common.factorytype.FactoryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FactoryFor {
    FactoryType[] type() default { FactoryType.DEFAULT };
}
