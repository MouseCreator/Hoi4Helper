package mouse.hoi.gamefiles.parser;

import mouse.hoi.gamefiles.parser.property.Property;


import java.lang.annotation.Annotation;
import java.util.List;


public interface PrimitivesParser {
    Object parsePrimitiveType(Class<?> clazz, List<Annotation> annotationList, Property property);
    Object parsePrimitiveType(String toParse, Class<?> clazz, List<Annotation> annotationList);
    boolean isPrimitiveClass(Class<?> clazz);



}
