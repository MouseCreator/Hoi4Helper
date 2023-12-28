package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.FromBlockValue;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
public class FieldHelper {
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

    public int initializeFields(Predicate<Field> fieldPredicate, Object model, Object value) {
        List<Field> fields = Arrays.asList(model.getClass().getDeclaredFields());
        fields.forEach(field -> field.setAccessible(true));
        List<Field> filtered = fields.stream().filter(fieldPredicate).toList();
        filtered.forEach(field -> setField(model, field, value));
        return filtered.size();
    }

    public Predicate<Field> withAnnotation(Class<? extends Annotation> annotation) {
        return field -> field.isAnnotationPresent(annotation);
    }

    private List<Field> getFields(Object model) {
        return Arrays.asList(toClass(model).getDeclaredFields());
    }

    public void initializeFieldsWithAnnotation(Object model, Class<? extends Annotation> annotation, Object value) {
        initializeFields(withAnnotation(FromBlockValue.class), model, value);
    }
}
