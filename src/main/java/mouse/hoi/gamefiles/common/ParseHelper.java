package mouse.hoi.gamefiles.common;

import mouse.hoi.exception.PropertyParseException;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;

@Service
public class ParseHelper {

    public boolean testGeneric(Field field, Object... toAdd)  {
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType parameterizedType)) {
            return false;
        }
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        if (typeArguments.length != toAdd.length) {
            return false;
        }
        for (int i = 0; i < typeArguments.length; i++) {
            Type listType = typeArguments[i];
            Object obj = toAdd[i];
            if(!listType.equals(obj.getClass())) {
                return false;
            }
        }
        return true;
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
            field.setAccessible(true);
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

    public List<Field> getFields(Object model) {
        return Arrays.asList(toClass(model).getDeclaredFields());
    }

    public List<Method> getMethods(Object model) {
        return Arrays.asList(model.getClass().getMethods());
    }

    public List<Method> getMethodsWithAnnotation(Object model, Class<? extends Annotation> annotation) {
        return getMethods(model).stream().filter(m -> m.isAnnotationPresent(annotation)).toList();
    }

    public List<Annotation> getAnnotations(Field field) {
        return Arrays.asList(field.getAnnotations());
    }

    public boolean isCollectionModel(Object modelAsValue) {
        return modelAsValue instanceof Collection<?>;
    }

    public Object getFieldValue(Object model, Field field) {
        try {
            field.setAccessible(true);
            return field.get(model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot get field " + field.getName()
                    + " of model " + model.getClass().getSimpleName(), e);
        }
    }

    public List<Class<?>> getAllGenerics(Field field) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            List<Class<?>> list = new ArrayList<>();
            for (Type type : typeArguments) {
                list.add((Class<?>) type);
            }
            return list;
        }
        throw new IllegalArgumentException("Generic type is not instance of Parametrized type for field: " + field);
    }
}
