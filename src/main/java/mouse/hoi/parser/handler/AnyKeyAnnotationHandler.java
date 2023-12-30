package mouse.hoi.parser.handler;

import mouse.hoi.parser.ParseHelper;
import mouse.hoi.parser.annotation.AnyKey;
import mouse.hoi.parser.property.input.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class AnyKeyAnnotationHandler implements AnnotationHandler{
    private final ParseHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    @Autowired
    public AnyKeyAnnotationHandler(ParseHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @Override
    public void handle(Object model, List<Property> propertyList) {
        List<Field> anyKeyFields = fieldHelper.getFieldsWithAnnotation(model, AnyKey.class);
        if (anyKeyFields.isEmpty()) {
            return;
        }
        annotationHandlerHelper.initialize(model, anyKeyFields, propertyList);
    }
}
