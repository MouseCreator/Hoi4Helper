package mouse.hoi.gamefiles.unparser.property;

import mouse.hoi.gamefiles.common.annotation.Simple;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.common.style.PrintStyle;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class OutputPropertyBuilder {
    private final OutputProperty property;
    private final List<Annotation> activeAnnotations;

    public OutputPropertyBuilder() {
        property = new OutputPropertyImpl();
        activeAnnotations = new ArrayList<>();
    }

    private void completeStyle() {
        if (property.getStyle()==null) {
            property.setStyle(PrintStyle.COMPLEX);
        }
    }
    private OutputProperty completeAndGet() {
        completeStyle();
        return property;
    }
    public OutputProperty simple() {
        toSimple();
        return completeAndGet();
    }

    public OutputProperty fieldValue() {
        toFieldValue();
        return completeAndGet();
    }
    public OutputProperty block() {
        toBlock();
        return completeAndGet();
    }

    public OutputPropertyBuilder withKey(String key) {
        property.setKey(key);
        return this;
    }

    public OutputPropertyBuilder withValue(String value) {
        property.setValue(value);
        return this;
    }

    public OutputPropertyBuilder withStyle(PrintStyle style) {
        property.setStyle(style);
        return this;
    }

    public OutputPropertyBuilder withChildren(OutputProperty child) {
        property.addChild(child);
        return this;
    }
    public OutputPropertyBuilder withChildren(Collection<OutputProperty> children) {
        property.addChildren(children);
        return this;
    }

    public OutputProperty createSimple(String value) {
        property.setValue(value);
        return simple();
    }

    public boolean hasKey() {
        String key = property.getKey();
        return isPresent(key);
    }

    private static boolean isPresent(String key) {
        return key != null && !key.isEmpty();
    }
    public boolean hasValue() {
        return isPresent(property.getValue());
    }
    public boolean hasChildren() {
        return !property.getChildren().isEmpty();
    }
    public boolean hasStyle() {
        return property.getStyle() != null;
    }

    public void toSimple() {
        property.setType(PropertyType.SIMPLE);
    }
    public void toFieldValue() {
        property.setType(PropertyType.FIELD_VALUE);
    }
    public void toBlock() {
        property.setType(PropertyType.BLOCK);
    }

    public OutputProperty get() {
        if (property.getType()==null) {
            throw new IllegalStateException("Trying to get property without type initialized " + property);
        }
        return property;
    }

    public boolean hasType() {
        return property.getType() != null;
    }

    public OutputPropertyBuilder duplicate() {
        OutputPropertyBuilder copy = new OutputPropertyBuilder();
        copy.property.setKey(property.getKey());
        copy.property.setType(property.getType());
        copy.property.setValue(property.getValue());
        copy.property.getChildren().addAll(property.getChildren());
        copy.property.setStyle(property.getStyle());
        copy.activeAnnotations.addAll(activeAnnotations);
        return copy;
    }

    public List<Annotation> getAnnotations() {
        return activeAnnotations;
    }

    public void withType(PropertyType t) {
        property.setType(t);
    }

    public void withAnnotations(List<Annotation> annotations) {
        activeAnnotations.addAll(annotations);
    }

    public <T extends Annotation> Optional<T> getAnnotation(Class<T> annotationClass) {
        for (Annotation annotation : activeAnnotations) {
            if (annotationClass.equals(annotation.annotationType())) {
                return Optional.of(annotationClass.cast(annotation));
            }
        }
        return Optional.empty();
    }
}
