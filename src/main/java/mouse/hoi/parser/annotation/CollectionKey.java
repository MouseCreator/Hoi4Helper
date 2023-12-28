package mouse.hoi.parser.annotation;

import mouse.hoi.parser.collectiontype.CollectionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CollectionKey {
    CollectionType type();
}
