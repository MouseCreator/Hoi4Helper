package mouse.hoi.gamefiles.parser;

import mouse.hoi.exception.PropertyParseException;
import mouse.hoi.gamefiles.common.annotation.Accuracy;
import mouse.hoi.gamefiles.common.annotation.UseQuotes;
import mouse.hoi.gamefiles.parser.property.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Service
public class PrimitivesParserImpl implements PrimitivesParser {
    private final StringFormatter stringFormatter;
    @Autowired
    public PrimitivesParserImpl(StringFormatter stringFormatter) {
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

    public Object parsePrimitiveType(String toParse, Class<?> clazz, List<Annotation> annotationList) {
        return clazz.isAssignableFrom(String.class) ? processStringValue(annotationList, toParse)
                : processNotStringValue(clazz, annotationList, toParse);
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

    private boolean hasAnnotation(List<Annotation> annotations, Class<?> annotationClass) {
        for (Annotation a : annotations) {
            if (annotationClass.isInstance(a)) {
                return true;
            }
        }
        return false;
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

    @Override
    public String convertToString(Object model, List<Annotation> toAnnotations) {
        String result = convert(model, toAnnotations);
        if (hasAnnotation(toAnnotations, UseQuotes.class)) {
            return stringFormatter.addQuotes(result);
        } else {
            return result;
        }
    }

    private String convert(Object model, List<Annotation> toAnnotations) {
        Class<?> aClass = model.getClass();
        if (aClass == Double.class) {
            return unparseDouble((Double) model, toAnnotations);
        }
        if (aClass == Boolean.class) {
            return unparseBoolean((Boolean) model);
        }
        return model.toString();
    }

    private String unparseBoolean(Boolean b) {
        return b ? "yes" : "no";
    }

    private String unparseDouble(Double val, List<Annotation> annotations) {
        int decimalPlaces = 3;
        Accuracy accuracyAnnotation = getDeclaredAnnotation(annotations, Accuracy.class);
        if (accuracyAnnotation != null) {
            decimalPlaces = accuracyAnnotation.digits();
        }
        String format = "%." + decimalPlaces + "f";
        String formatted = String.format(Locale.ENGLISH, format, val);
        DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.#", decimalFormatSymbols);
        decimalFormat.setMaximumFractionDigits(decimalPlaces);
        decimalFormat.setMinimumFractionDigits(1);
        return decimalFormat.format(Double.parseDouble(formatted));
    }
}
