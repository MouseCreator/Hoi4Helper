package mouse.hoi.gamefiles.unparser.property;

import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.common.style.PrintStyle;

import java.util.Collection;

public class OutputPropertyBuilder {
    private final OutputProperty property;

    public OutputPropertyBuilder() {
        property = new OutputPropertyImpl();
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
        property.setType(PropertyType.SIMPLE);
        return completeAndGet();
    }

    public OutputProperty fieldValue() {
        property.setType(PropertyType.FIELD_VALUE);
        return completeAndGet();
    }
    public OutputProperty block() {
        property.setType(PropertyType.BLOCK);
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
}
