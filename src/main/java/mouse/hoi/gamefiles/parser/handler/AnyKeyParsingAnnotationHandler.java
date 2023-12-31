package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.AnyKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class AnyKeyParsingAnnotationHandler implements ParsingAnnotationHandler {
    private final ParseHelper fieldHelper;
    private final AnnotationHandlerHelper annotationHandlerHelper;
    @Autowired
    public AnyKeyParsingAnnotationHandler(ParseHelper fieldHelper, AnnotationHandlerHelper annotationHandlerHelper) {
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
