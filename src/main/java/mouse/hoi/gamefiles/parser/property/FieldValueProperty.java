package mouse.hoi.gamefiles.parser.property;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@ToString
@NoArgsConstructor
public class FieldValueProperty implements Property {
    private String key;
    private String value;

    public FieldValueProperty(String key, String value) {
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
    public List<Property> getChildren() {
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
