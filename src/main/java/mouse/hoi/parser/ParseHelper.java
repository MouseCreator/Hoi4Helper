package mouse.hoi.parser;

import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ParseHelper {

    public void push(Object model, Field field, Object toAdd) {
        try {
            pushToList(model, field, toAdd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void pushToList(Object model, Field field, Object toAdd) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object list;
        if (field.getType().isInterface()) {
            list = new ArrayList<>();
        } else {
            list = field.getType().getConstructor().newInstance();
        }
        boolean valid = testGeneric(field, list, toAdd);
        if (!valid) {
            throw new IllegalArgumentException("Parsing collection field " + field.getName() + ": Argument is not of Generic type!");
        }
        if (list instanceof List) {
            Method add = List.class.getDeclaredMethod("add", Object.class);
            add.invoke(list, toAdd);
            field.set(model, list);
        } else if (list instanceof Set) {
            Method add = Set.class.getDeclaredMethod("add", Object.class);
            add.invoke(list, toAdd);
            field.set(model, list);
        }
    }
    private boolean testGeneric(Field field, Object list, Object toAdd)  {
        if (list instanceof List || list instanceof Set) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType parameterizedType) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                if (typeArguments.length == 1) {
                    Type listType = typeArguments[0];
                    return listType.equals(toAdd.getClass());
                }
            }
        }
        return false;
    }

    public boolean isCollectionField(Field field) {
        Class<?> fieldType = field.getType();
        return List.class.isAssignableFrom(fieldType) || Set.class.isAssignableFrom(fieldType);
    }
}
