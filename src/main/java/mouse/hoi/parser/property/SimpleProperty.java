package mouse.hoi.parser.property;

import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode
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
        throw new UnsupportedOperationException("SimpleProperty does not have key");
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

    public void setValue(String value) {
        this.value = value;
    }
}
