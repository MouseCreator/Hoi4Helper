package mouse.hoi.gamefiles.parser.handler;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.DefaultField;
import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
@Service
public class ParsingDefaultInitializationHandler {
    private final AnnotationHandlerHelper annotationHandlerHelper;
    private final ParseHelper parseHelper;
    @Autowired
    public ParsingDefaultInitializationHandler(AnnotationHandlerHelper annotationHandlerHelper, ParseHelper parseHelper) {
        this.annotationHandlerHelper = annotationHandlerHelper;
        this.parseHelper = parseHelper;
    }

    public void initDefaultFields(Object model, Property property) {
        List<Field> fields = parseHelper.getFieldsWithAnnotation(model, DefaultField.class);
        if (fields.isEmpty()) {
            throw new PropertyParseException("Non-Block Property " + property.print() + " is used to defined a model "
                    + model.getClass().getSimpleName() + ", but no default field is found");
        }
        annotationHandlerHelper.initialize(model, fields, List.of(property));
    }
}
