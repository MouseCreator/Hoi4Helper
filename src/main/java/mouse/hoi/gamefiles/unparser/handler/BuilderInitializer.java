package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Ordered;
import mouse.hoi.gamefiles.unparser.InitializerCaller;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class BuilderInitializer implements InitializerCaller {
    private final ParseHelper parseHelper;
    private OutputPropertyInitializer initializer;
    private final DefaultFieldHandler defaultFieldHandler;
    @Autowired
    public BuilderInitializer(ParseHelper parseHelper, DefaultFieldHandler defaultFieldHandler) {
        this.parseHelper = parseHelper;
        this.defaultFieldHandler = defaultFieldHandler;
    }
    @Override
    public void setInitializer(OutputPropertyInitializer initializer) {
        this.initializer = initializer;
    }

    public List<OutputPropertyBuilder> initializeProperties(Object model, List<Field> fieldList) {
        List<OutputPropertyBuilder> result = new ArrayList<>();
        result.addAll(handleOrderedFields(model, fieldList));
        result.addAll(handleNotOrderedFields(model, fieldList));
        return result;
    }

    public List<OutputProperty> toProperty(Object model, List<OutputPropertyBuilder> builders) {
        List<OutputProperty> properties = new ArrayList<>();
        for (OutputPropertyBuilder builder : builders) {
            properties.addAll(initializer.initializeProperty(model, builder));
        }
        return properties;
    }


    private List<OutputPropertyBuilder> handleOrderedFields(Object model, List<Field> fieldList) {
        HashMap<Integer,Field> orderedFields = getWithOrderAnnotation(fieldList);
        List<OutputPropertyBuilder> list = new ArrayList<>();
        for (int i = 0; i < orderedFields.size(); i++) {
            Field field = orderedFields.get(i);
            if (field == null) {
                throw new UnparsingException("For ordered fields, cannot find field with order "
                        + i + " while parsing "
                        + model.getClass().getSimpleName());
            }
            initAndAddProperty(model, list, field);
        }
        return list;
    }

    private void initAndAddProperty(Object model, List<OutputPropertyBuilder> list, Field field) {
        if (isDefaultFields(model, field)) {
            return;
        }
        OutputPropertyBuilder property = createPropertyFromField(field);
        list.add(property);
    }

    private OutputPropertyBuilder createPropertyFromField(Field field) {
        OutputPropertyBuilder builder = new OutputPropertyBuilder();
        builder.withAnnotations(parseHelper.getAnnotations(field));
        return builder;
    }
    private HashMap<Integer, Field> getWithOrderAnnotation(List<Field> fieldList) {
        HashMap<Integer, Field> fieldMap = new HashMap<>();
        for (Field field : fieldList) {
            Ordered orderedAnnotation = field.getAnnotation(Ordered.class);
            if (orderedAnnotation != null) {
                int order = orderedAnnotation.num();
                fieldMap.put(order, field);
            }
        }
        return fieldMap;
    }

    private List<OutputPropertyBuilder> handleNotOrderedFields(Object model, List<Field> fieldList) {
        List<Field> notOrderedFields = fieldList.stream()
                .filter(f -> !f.isAnnotationPresent(Ordered.class)).toList();
        List<OutputPropertyBuilder> list = new ArrayList<>();
        for (Field field : notOrderedFields) {
            initAndAddProperty(model, list, field);
        }
        return list;
    }

    private boolean isDefaultFields(Object model, Field field) {
        return defaultFieldHandler.isDefaultField(model, field);
    }
}
