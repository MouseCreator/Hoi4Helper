package mouse.hoi.gamefiles.parser.collection;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;
@Service
public class CollectionParserCommon {
    public boolean isType(Class<?> clazz, Field field) {
        Class<?> fieldType = field.getType();
        return clazz.isAssignableFrom(fieldType);
    }

    public Object getCollectionObject(Object instance, Field field, Supplier<Object> onAbsent)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Object list = field.get(instance);
        if (list != null) {
            return list;
        }
        if (field.getType().isInterface()) {
            list = onAbsent.get();
        } else {
            list = field.getType().getConstructor().newInstance();
        }
        return list;
    }
}
