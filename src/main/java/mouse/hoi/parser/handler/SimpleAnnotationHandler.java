package mouse.hoi.parser.handler;

import mouse.hoi.parser.FieldHelper;
import mouse.hoi.parser.annotation.ObjField;
import mouse.hoi.parser.annotation.Simple;
import mouse.hoi.parser.property.Property;
import mouse.hoi.parser.property.PropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
@Service
public class SimpleAnnotationHandler implements AnnotationHandler{
    private final FieldHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    @Autowired
    public SimpleAnnotationHandler(FieldHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
        this.fieldHelper = fieldHelper;
        this.annotationHandlerHelper = annotationHandlerHelper;
    }

    @Override
    public void handle(Object model, List<Property> propertyList) {
        List<Field> simpleFields = fieldHelper.getFieldsWithAnnotation(model, Simple.class);
        List<Property> simpleProperties = getSimpleAnnotations(propertyList);
        annotationHandlerHelper.initialize(model, simpleFields, simpleProperties);
    }

    private List<Field> getFieldsByKey(List<Field> fields, String key) {
        return fields.stream().filter(field -> key.equals(field.getAnnotation(ObjField.class).text())).toList();
    }

    private List<Property> getSimpleAnnotations(List<Property> propertyList) {
        return propertyList.stream()
                .filter(p -> p.type()==PropertyType.SIMPLE)
                .toList();
    }
}
