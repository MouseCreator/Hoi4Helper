package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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

    public Class<?> getGeneric(Field field) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length > 0) {
                return (Class<?>) typeArguments[0];
            }
            throw new IllegalArgumentException("No generic type for field: " + field);
        }
        throw new IllegalArgumentException("Generic type is not instance of Parametrized type for field: " + field);

    }

    public void setField(Object model, Field field, Object instance) {
        try {
            field.set(model, instance);
        } catch (IllegalAccessException e) {
            throw new PropertyParseException("Cannot set value to field " + field.getName() + ", value: " + instance, e);
        }
    }

    public List<Field> getFieldsWithAnnotation(Object model, Class<? extends Annotation> annotation) {
        return getFields(model).stream().filter(withAnnotation(annotation)).toList();
    }

    public Class<?> toClass(Object model) {
        return model.getClass();
    }

    private Predicate<Field> withAnnotation(Class<? extends Annotation> annotation) {
        return field -> field.isAnnotationPresent(annotation);
    }

    private List<Field> getFields(Object model) {
        return Arrays.asList(toClass(model).getDeclaredFields());
    }

    public List<Annotation> getAnnotations(Field field) {
        return Arrays.asList(field.getAnnotations());
    }
}
