package mouse.hoi.gamefiles.unparser.property;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.common.style.PrintStyle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@EqualsAndHashCode
@ToString
public class OutputPropertyImpl implements OutputProperty {
    private String key;
    private String value;
    private PropertyType type;
    private PrintStyle printStyle;
    private final List<OutputProperty> children;
    public OutputPropertyImpl() {
        key = "";
        value = "";
        type = PropertyType.BLOCK;
        printStyle = null;
        children = new ArrayList<>();
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setType(PropertyType propertyType) {
        type = propertyType;
    }

    @Override
    public void setStyle(PrintStyle printStyle) {
        this.printStyle = printStyle;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public List<OutputProperty> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public PropertyType getType() {
        return type;
    }

    @Override
    public PrintStyle getStyle() {
        return printStyle;
    }

    @Override
    public void addChild(OutputProperty newProperty) {
        children.add(newProperty);
    }

    @Override
    public void addChildren(Collection<OutputProperty> newProperty) {
        children.addAll(newProperty);
    }
}
