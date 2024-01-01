package mouse.hoi.gamefiles.unparser.handler.children;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.FromKeyValue;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class KeyValueAnnotationApplier implements BuilderAnnotationApplier {
    private ParseHelper parseHelper;
    private OutputPropertyInitializer initializer;
    @Override
    public void apply(OutputPropertyBuilder builder, Object model) {
        List<Field> fromBlockValue = parseHelper.getFieldsWithAnnotation(model.getClass(), FromKeyValue.class);
        if (fromBlockValue.isEmpty()) {
            return;
        }
        if (fromBlockValue.size() > 1) {
            throw new UnparsingException("Unable to paste multiple FromBlockValue to property value for" + model.getClass().getSimpleName());
        }
        Field field = fromBlockValue.get(0);
        Object fieldValue = parseHelper.getFieldValue(model, field);
        String value = convert(fieldValue);
        builder.withKey(value);
    }

    private String convert(Object fieldValue) {
        List<OutputProperty> properties = initializer.initializeProperty(fieldValue);
        if (properties.size() != 1) {
            throw new UnparsingException("Cannot put as KeyValue: "
                    + fieldValue.getClass().getSimpleName()
                    + ", because it is parsed to multiple properties: " + properties.size());
        }
        OutputProperty property = properties.get(0);
        if (property.getType()== PropertyType.BLOCK) {
            throw new UnparsingException("Cannot put a block as KeyValue for " + fieldValue.getClass().getSimpleName());
        }
        return property.getValue();
    }
}
