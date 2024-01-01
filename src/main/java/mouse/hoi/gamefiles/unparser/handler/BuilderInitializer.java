package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Ordered;
import mouse.hoi.gamefiles.unparser.InitializerCaller;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.handler.build.ForEachBuilder;
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

    public List<OutputProperty> initializeProperties(Object model, List<Field> fieldList, ForEachBuilder consumer) {
        return initializeWithBuilders(model, fieldList, consumer);
    }

    public List<OutputProperty> initializeProperties(Object model, List<Field> fields) {
        return initializeProperties(model, fields, b->{});
    }

    private List<OutputProperty> createChildProperty(Object child, OutputPropertyBuilder builder) {
        return initializer.initializeProperty(child, builder);
    }

    private List<OutputProperty> initializeWithBuilders(Object model, List<Field> fieldList, ForEachBuilder consumer) {
        List<OutputProperty> result = new ArrayList<>();
        result.addAll(handleOrderedFields(model, fieldList, consumer));
        result.addAll(handleNotOrderedFields(model, fieldList, consumer));
        return result;
    }


    private List<OutputProperty> handleOrderedFields(Object model, List<Field> fieldList, ForEachBuilder consumer) {
        HashMap<Integer,Field> orderedFields = getWithOrderAnnotation(fieldList);
        List<OutputProperty> list = new ArrayList<>();
        for (int i = 0; i < orderedFields.size(); i++) {
            Field field = orderedFields.get(i);
            if (field == null) {
                throw new UnparsingException("For ordered fields, cannot find field with order "
                        + i + " while parsing "
                        + model.getClass().getSimpleName());
            }
            initAndAddProperty(model, list, field, consumer);
        }
        return list;
    }

    private void initAndAddProperty(Object model, List<OutputProperty> list, Field field, ForEachBuilder consumer) {
        if (isDefaultFields(model, field)) {
            return;
        }
        Object fieldValue = parseHelper.getFieldValue(model, field);
        OutputPropertyBuilder builder = createPropertyFromField(field, consumer);
        List<OutputProperty> childProperty = createChildProperty(fieldValue, builder);
        list.addAll(childProperty);
    }

    private OutputPropertyBuilder createPropertyFromField(Field field, ForEachBuilder consumer) {
        OutputPropertyBuilder builder = new OutputPropertyBuilder();
        builder.withAnnotations(parseHelper.getAnnotations(field));
        consumer.accept(builder);
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

    private List<OutputProperty> handleNotOrderedFields(Object model, List<Field> fieldList, ForEachBuilder consumer) {
        List<Field> notOrderedFields = fieldList.stream()
                .filter(f -> !f.isAnnotationPresent(Ordered.class)).toList();
        List<OutputProperty> list = new ArrayList<>();
        for (Field field : notOrderedFields) {
            initAndAddProperty(model, list, field, consumer);
        }
        return list;
    }

    private boolean isDefaultFields(Object model, Field field) {
        return defaultFieldHandler.isDefaultField(model, field);
    }

}
