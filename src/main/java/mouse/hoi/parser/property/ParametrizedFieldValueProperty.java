package mouse.hoi.parser.property;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@EqualsAndHashCode
public class ParametrizedFieldValueProperty<T extends BaseProperty> implements ParametrizedProperty<T> {
    protected String key;
    protected String value;

    public ParametrizedFieldValueProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isBlock() {
        return false;
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
    public boolean isEmpty() {
        return value.isEmpty();
    }

    @Override
    public List<T> getChildren() {
        throw new UnsupportedOperationException("FieldValueProperty does not have child properties");
    }

    @Override
    public String print() {
        return key + "=" + value + ";";
    }

    @Override
    public PropertyType type() {
        return PropertyType.FIELD_VALUE;
    }
}
