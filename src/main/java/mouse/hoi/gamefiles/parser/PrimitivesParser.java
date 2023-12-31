package mouse.hoi.gamefiles.parser;

import mouse.hoi.gamefiles.parser.property.Property;


import java.lang.annotation.Annotation;
import java.util.List;


public interface PrimitivesParser {
    Object parsePrimitiveType(Class<?> clazz, List<Annotation> annotationList, Property property);
    boolean isPrimitiveClass(Class<?> clazz);

}