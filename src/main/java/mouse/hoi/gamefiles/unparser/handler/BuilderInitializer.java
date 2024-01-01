package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Ordered;
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
public class BuilderInitializer {
    private final ParseHelper parseHelper;
    private OutputPropertyInitializer initializer;
    @Autowired
    public BuilderInitializer(ParseHelper parseHelper) {
        this.parseHelper = parseHelper;
    }

    public void setInitializer(OutputPropertyInitializer initializer) {
        this.initializer = initializer;
    }

    public List<OutputPropertyBuilder> initializeProperties(Object model, List<Field> fieldList) {
        List<OutputPropertyBuilder> result = new ArrayList<>();
        result.addAll(handleOrderedFields(model, fieldList));
        result.addAll(handleNotOrderedFields(fieldList));
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
            list.add(createPropertyFromField(field));
        }
        return list;
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

    private List<OutputPropertyBuilder> handleNotOrderedFields(List<Field> fieldList) {
        List<Field> notOrderedFields = fieldList.stream()
                .filter(f -> !f.isAnnotationPresent(Ordered.class)).toList();
        List<OutputPropertyBuilder> list = new ArrayList<>();
        for (Field field : notOrderedFields) {
            list.add(createPropertyFromField(field));
        }
        return list;
    }
}
