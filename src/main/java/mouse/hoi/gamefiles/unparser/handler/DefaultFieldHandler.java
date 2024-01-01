package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.DefaultField;
import mouse.hoi.gamefiles.common.annotation.OmitIfDefault;
import mouse.hoi.gamefiles.common.annotation.OmitIfEquals;
import mouse.hoi.gamefiles.parser.ModelCreator;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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

    public boolean allowsDefaultInitialization(Object model) {
        List<Field> defaultInit = parseHelper.getFieldsWithAnnotation(model, DefaultField.class);
        if (defaultInit.isEmpty()) {
            return false;
        }
        return canOmitOtherFields(model);
    }

    private boolean canOmitOtherFields(Object model) {
        try {
            Object defaultValue = modelCreator.lookup(model.getClass());
            return compareWithDefaultAndExpectedValues(model, defaultValue);
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    private boolean compareWithDefaultAndExpectedValues(Object model, Object defaultValue) {
        List<Field> differentFields = compareWithDefaultExceptDefFields(defaultValue, model);
        for (Field field : differentFields) {
            if (!handleOmitIfEquals(model, field)) {
                return false;
            }
        }
        return true;
    }

    private List<Field> compareWithDefaultExceptDefFields(Object model, Object defModel) {
        List<Field> differentFields = new ArrayList<>();
        for (Field field : model.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DefaultField.class)) {
                continue;
            }
            Object value1 = parseHelper.getFieldValue(model, field);
            Object value2 = parseHelper.getFieldValue(defModel, field);
            if (!Objects.equals(value1, value2)) {
                differentFields.add(field);
            }
        }
        return differentFields;
    }

    public Object getDefaultFieldValue(Object model) {
        List<Field> defaultFields = parseHelper.getFieldsWithAnnotation(model, DefaultField.class);
        if (defaultFields.isEmpty()) {
            throw new UnparsingException("No default fields found in model " + model.getClass().getSimpleName());
        }
        Field field = defaultFields.get(0);
        Object fieldValue = parseHelper.getFieldValue(model, field);
        if (defaultFields.size() > 1) {
            validateFieldValue(model, defaultFields, field, fieldValue);
        }
        return fieldValue;
    }

    private void validateFieldValue(Object model, List<Field> defaultFields, Field field, Object fieldValue) {
        for (int i = 1; i < defaultFields.size(); i++) {
            Field otherField = defaultFields.get(i);
            Object otherValue = parseHelper.getFieldValue(model, otherField);
            if (!Objects.equals(fieldValue, otherValue)) {
                throw new UnparsingException(
                        String.format("Different default values in fields %s and %s for model %s",
                                field.getName(), otherField, model.getClass().getSimpleName()));
            }
        }
    }
}
