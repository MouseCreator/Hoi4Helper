package mouse.hoi.gamefiles.common.annotation;

import mouse.hoi.gamefiles.common.collectiontype.CollectionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CollectionKey {
    CollectionType type();
}
