package mouse.hoi.gamefiles.common;

import mouse.hoi.exception.PropertyParseException;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Predicate;

@Service
public class ParseHelper {

    public void push(Object model, Field field, Object toAdd) {
        try {
            field.setAccessible(true);
            pushToList(model, field, toAdd);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void pushToList(Object model, Field field, Object toAdd) throws InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object list = getCollectionObject(model, field);
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

    private Object getCollectionObject(Object instance, Field field)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Object list = field.get(instance);
        if (list != null) {
           return list;
        }
        if (field.getType().isInterface()) {
            if (field.getType().isAssignableFrom(List.class)) {
                list = new ArrayList<>();
            } else if (field.getType().isAssignableFrom(Set.class)) {
                list = new HashSet<>();
            } else {
                throw new IllegalAccessException("Collection field in neither List nor Set");
            }
        } else {
            list = field.getType().getConstructor().newInstance();
        }
        return list;
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

    private List<Field> getFields(Object model) {
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
        return modelAsValue.getClass().isAssignableFrom(Collection.class);
    }

    public Object getFieldValue(Object model, Field field) {
        field.setAccessible(true);
        try {
            return field.get(model);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot get field " + field.getName()
                    + " of model " + model.getClass().getSimpleName(), e);
        }
    }
}