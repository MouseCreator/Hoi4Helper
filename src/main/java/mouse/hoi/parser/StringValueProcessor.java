package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.Accuracy;
import mouse.hoi.parser.annotation.UseQuotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
@Component
public class StringValueProcessor {

    private final StringFormatter formatter;
    @Autowired
    public StringValueProcessor(StringFormatter formatter) {
        this.formatter = formatter;
    }

    public Object getFromString(Field field, String value) {
        Class<?> type = field.getType();
        return (String.class.isAssignableFrom(type)) ?
                processStringValue(field, value) : processNotStringValue(field, value);
    }

    private String processStringValue(Field field, String value) {
        if (field.isAnnotationPresent(UseQuotes.class)) {
            value = formatter.removeQuotes(value);
        }
        return value;
    }

    private Object processNotStringValue(Field field, String value) {
        Class<?> clazz = field.getType();
        if (clazz == Integer.class) {
            return Integer.parseInt(value);
        } else if (clazz == Long.class) {
            return Long.parseLong(value);
        } else if (clazz == Double.class) {
            return processDouble(field, value);
        } else if (clazz == Boolean.class) {
            return processBoolean(value);
        } else {
            throw new IllegalArgumentException("Unknown field type for String value processor: "+ clazz.getSimpleName());
        }
    }

    private double processDouble(Field field, String value) {
        Accuracy accuracyAnnotation = field.getDeclaredAnnotation(Accuracy.class);
        if (accuracyAnnotation != null) {
            int decimalPlaces = accuracyAnnotation.digits();
            return Double.parseDouble(String.format("%." + decimalPlaces + "f", Double.parseDouble(value)));
        } else {
            return Double.parseDouble(value);
        }
    }

    private Object processBoolean(String value) {
        if ("yes".equalsIgnoreCase(value)) {
            return true;
        } else if ("no".equalsIgnoreCase(value)) {
            return false;
        } else {
            throw new PropertyParseException("Invalid value for Boolean: " + value);
        }
    }
}
