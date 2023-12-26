package mouse.hoi.parser.annotation;

import mouse.hoi.parser.style.PrintStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Style {
    PrintStyle printStyle() default PrintStyle.COMPLEX;
}
