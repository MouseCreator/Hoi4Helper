package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.OmitIfDefault;
import mouse.hoi.gamefiles.common.annotation.OmitIfEquals;
import mouse.hoi.gamefiles.parser.ModelCreator;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class DefaultFieldHandler {
    private final ParseHelper parseHelper;
    private final PrimitivesParser primitivesParser;
    private final ModelCreator modelCreator;

    public DefaultFieldHandler(ParseHelper parseHelper, PrimitivesParser primitivesParser, ModelCreator modelCreator) {
        this.parseHelper = parseHelper;
        this.primitivesParser = primitivesParser;
        this.modelCreator = modelCreator;
    }

    public boolean isDefaultField(Object model, Field field) {
        if (field.isAnnotationPresent(OmitIfDefault.class)) {
            return handleOmitIfDefault(model, field);
        }
        if (field.isAnnotationPresent(OmitIfEquals.class)) {
            return handleOmitIfEquals(model, field);
        }
        return false;
    }

    private boolean handleOmitIfDefault(Object model, Field field) {
        Class<?> type = field.getType();
        try {
            Object defaultValue = modelCreator.lookup(type);
            Object actualValue = parseHelper.getFieldValue(model, field);
            return Objects.equals(defaultValue, actualValue);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean handleOmitIfEquals(Object model, Field field) {
        OmitIfEquals omitIfEquals = field.getAnnotation(OmitIfEquals.class);
        String value = omitIfEquals.value();
        Object expectedValue = primitivesParser.parsePrimitiveType(value, field.getType(), parseHelper.getAnnotations(field));
        Object actualValue = parseHelper.getFieldValue(model, field);
        return Objects.equals(expectedValue, actualValue);
    }
}
