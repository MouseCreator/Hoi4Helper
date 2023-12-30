package mouse.hoi.parser.property;

import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode
public class ParametrizedSimpleProperty<T extends BaseProperty> implements ParametrizedProperty<T> {
    protected String value;
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
    public List<T> getChildren() {
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
