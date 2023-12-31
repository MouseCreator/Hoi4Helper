package mouse.hoi.gamefiles.parser;

import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;


import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;


public interface PrimitivesParser {
    Object parsePrimitiveType(Class<?> clazz, List<Annotation> annotationList, Property property);
    Object parsePrimitiveType(String toParse, Class<?> clazz, List<Annotation> annotationList);
    boolean isPrimitiveClass(Class<?> clazz);
    String convertToString(Object model, List<Annotation> toAnnotations);
}
