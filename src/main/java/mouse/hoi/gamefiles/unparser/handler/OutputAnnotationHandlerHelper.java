package mouse.hoi.gamefiles.unparser.handler;

import mouse.hoi.exception.UnparsingException;
import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.Ordered;
import mouse.hoi.gamefiles.unparser.CreationParameters;
import mouse.hoi.gamefiles.unparser.OutputPropertyInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class OutputAnnotationHandlerHelper {
    private final ParseHelper parseHelper;
    private final OutputPropertyInitializer outputPropertyInitializer;
    private final CreationParametersFactory creationParametersFactory;
    @Autowired
    public OutputAnnotationHandlerHelper(ParseHelper parseHelper,
                                         OutputPropertyInitializer outputPropertyInitializer,
                                         CreationParametersFactory creationParametersFactory) {

        this.parseHelper = parseHelper;
        this.outputPropertyInitializer = outputPropertyInitializer;
        this.creationParametersFactory = creationParametersFactory;
    }

    public List<OutputProperty> initializeProperties(Object model, List<Field> fieldList) {
        List<OutputProperty> result = new ArrayList<>();
        result.addAll(handleOrderedFields(model, fieldList));
        result.addAll(handleNotOrderedFields(model, fieldList));
        return result;
    }


    private List<OutputProperty> handleOrderedFields(Object model, List<Field> fieldList) {
        HashMap<Integer,Field> orderedFields = getWithOrderAnnotation(fieldList);
        List<OutputProperty> list = new ArrayList<>();
        for (int i = 0; i < orderedFields.size(); i++) {
            Field field = orderedFields.get(i);
            if (field == null) {
                throw new UnparsingException("For ordered fields, cannot find field with order "
                        + i + " while parsing "
                        + model.getClass().getSimpleName());
            }
            list.add(createPropertyFromField(model, field));
        }
        return list;
    }

    private OutputProperty createPropertyFromField(Object model, Field field) {
        CreationParameters creationParameters = creationParametersFactory.create(field);
        Object fieldValue = parseHelper.getFieldValue(model, field);
        return outputPropertyInitializer.initializeProperty(fieldValue, creationParameters);
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

    private List<OutputProperty> handleNotOrderedFields(Object model, List<Field> fieldList) {
        List<Field> notOrderedFields = fieldList.stream()
                .filter(f -> !f.isAnnotationPresent(Ordered.class)).toList();
        List<OutputProperty> list = new ArrayList<>();
        for (Field field : notOrderedFields) {
            list.add(createPropertyFromField(model, field));
        }
        return list;
    }
}
