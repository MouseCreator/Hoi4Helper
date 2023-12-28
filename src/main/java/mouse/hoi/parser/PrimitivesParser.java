package mouse.hoi.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.parser.annotation.Accuracy;
import mouse.hoi.parser.annotation.UseQuotes;
import mouse.hoi.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;

@Service
public class PrimitivesParser {

    private final StringFormatter stringFormatter;
    @Autowired
    public PrimitivesParser(StringFormatter stringFormatter) {
        this.stringFormatter = stringFormatter;
    }

    public Object parsePrimitiveType(Class<?> clazz, List<Annotation> annotationList, Property property) {
        if (property.isBlock()) {
            throw new PropertyParseException("Primitive type " + clazz.getSimpleName() + " is defined with block " + property.getKey());
        }
        String value = property.getValue();
        return clazz.isAssignableFrom(String.class) ? processStringValue(annotationList, value)
                : processNotStringValue(clazz, annotationList, value);
    }

    public boolean isPrimitiveClass(Class<?> clazz) {
        return List.of(String.class, Integer.class, Boolean.class, Long.class, Double.class).contains(clazz);
    }

    private String processStringValue(List<Annotation> annotations, String value) {
        if (hasAnnotation(annotations, UseQuotes.class)) {
            value = stringFormatter.removeQuotes(value);
        }
        return value;
    }

    private boolean hasAnnotation(List<Annotation> annotations, Class<?> annotation) {
        return annotations.stream().map(Annotation::getClass).anyMatch(c -> c.equals(annotation));
    }
    private <T extends Annotation> T getDeclaredAnnotation(List<Annotation> annotations, Class<T> target) {
        Optional<Annotation> targetAnnotation = annotations.stream().filter(a -> target.equals(a.getClass())).findFirst();
        return targetAnnotation.map(target::cast).orElse(null);
    }

    private Object processNotStringValue(Class<?> clazz, List<Annotation> annotations, String value) {
        if (clazz == Integer.class) {
            return Integer.parseInt(value);
        } else if (clazz == Long.class) {
            return Long.parseLong(value);
        } else if (clazz == Double.class) {
            return processDouble(annotations, value);
        } else if (clazz == Boolean.class) {
            return processBoolean(value);
        } else {
            throw new IllegalArgumentException("Unknown field type for String value processor: "+ clazz.getSimpleName());
        }
    }

    private double processDouble(List<Annotation> annotations, String value) {
        Accuracy accuracyAnnotation = getDeclaredAnnotation(annotations, Accuracy.class);
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
