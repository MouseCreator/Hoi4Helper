package mouse.hoi.gamefiles.unparser.handler.initialization;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.DefaultField;
import mouse.hoi.gamefiles.common.annotation.OmitIfDefault;
import mouse.hoi.gamefiles.common.annotation.OmitIfEquals;
import mouse.hoi.gamefiles.parser.ModelCreator;
import mouse.hoi.gamefiles.parser.PrimitivesParser;
import mouse.hoi.gamefiles.unparser.ModelToPropertyUnparser;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class DefaultInitializationHandler implements InitializationHandler {

    private final ModelCreator modelCreator;
    private final ParseHelper parseHelper;
    private final ModelToPropertyUnparser unparser;
    private final PrimitivesParser primitivesParser;
    @Autowired
    public DefaultInitializationHandler(ModelCreator modelCreator,
                                        ParseHelper parseHelper,
                                        ModelToPropertyUnparser unparser, PrimitivesParser primitivesParser) {
        this.modelCreator = modelCreator;
        this.parseHelper = parseHelper;
        this.unparser = unparser;
        this.primitivesParser = primitivesParser;
    }
    @Override
    public Optional<OutputProperty> handleInitialization(Object model, OutputPropertyBuilder builder, List<Annotation> annotations) {
        return initializeWithDefaultValue(model, builder);
    }
    private Optional<OutputProperty> initializeWithDefaultValue(Object model, OutputPropertyBuilder builder) {
        List<Field> defaultFields = parseHelper.getFieldsWithAnnotation(model, DefaultField.class);
        if (defaultFields.isEmpty()) {
            return Optional.empty();
        }
        if (defaultFields.size() > 1) {
            throw new UnparsingException("More than one default field for model "+ model.getClass() + ": " + defaultFields);
        }

        Object defaultModel = modelCreator.lookup(model.getClass());

        if (canUseDefaultInitialization(model, defaultModel)) {
            Field defaultField = defaultFields.get(0);
            Object fieldValue = parseHelper.getFieldValue(model, defaultField);
            OutputProperty property = applyDefaultInitialization(fieldValue, builder);
            return Optional.of(property);
        }
        return Optional.empty();
    }

    private OutputProperty applyDefaultInitialization(Object fieldValue, OutputPropertyBuilder builder) {
        throw new UnsupportedOperationException();
    }

    private boolean canUseDefaultInitialization(Object model, Object defaultModel) {
        if (model == defaultModel) return true;
        if (model == null || defaultModel == null) return false;
        if (model.getClass() != defaultModel.getClass()) return false;
        return compareFieldsWithDefaultValue(model, defaultModel) && compareFieldsWithExpectedValue(model);
    }
    private boolean compareFieldsWithDefaultValue(Object model, Object defaultModel) {
        List<Field> fieldsWithExpectedValue = parseHelper.getFieldsWithAnnotation(model, OmitIfDefault.class);
        for (Field field : fieldsWithExpectedValue) {
            Object actualValue = parseHelper.getFieldValue(model, field);
            Object defaultValue = parseHelper.getFieldValue(defaultModel, field);
            if (!Objects.equals(actualValue, defaultValue)) {
                return false;
            }
        }
        return true;
    }
    private boolean compareFieldsWithExpectedValue(Object model) {
        List<Field> fieldsWithExpectedValue = parseHelper.getFieldsWithAnnotation(model, OmitIfEquals.class);
        for (Field field : fieldsWithExpectedValue) {
            OmitIfEquals omitIfEquals = field.getAnnotation(OmitIfEquals.class);
            String expectedString = omitIfEquals.value();
            Object actualValue = parseHelper.getFieldValue(model, field);
            if (!primitivesParser.isPrimitiveClass(actualValue.getClass())) {
                throw new UnparsingException(OmitIfEquals.class.getSimpleName() +
                        " is used over non-primitive class variable");
            }
            Object expectedValue = primitivesParser.parsePrimitiveType(expectedString, field.getType(),
                    parseHelper.getAnnotations(field));
            if (!Objects.equals(actualValue, expectedValue)) {
                return false;
            }
        }
        return true;
    }
    private boolean compareWithDefault(Object model, Object defaultModel) {

        for (Field field : model.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DefaultField.class)) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value1 = field.get(model);
                Object value2 = field.get(defaultModel);

                if (!Objects.equals(value1, value2)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }


}
