package mouse.hoi.parser.property;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
@EqualsAndHashCode
@ToString
public class SimpleProperty implements Property {

    private String value;

    public SimpleProperty(String value) {
        this.value = value;
    }

    public SimpleProperty() {
        this.value = "";
    }

    @Override
    public boolean isBlock() {
        return false;
    }

    @Override
    public String getKey() {
        throw new UnsupportedOperationException("SimpleProperty does not have a key");
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public List<Property> getChildren() {
        throw new UnsupportedOperationException("SimpleProperty does not have child properties");
    }

    @Override
    public String print() {
        return value + ";";
    }

    @Override
    public PropertyType type() {
        return PropertyType.SIMPLE;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
