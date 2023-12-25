package mouse.hoi.parser.property;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@EqualsAndHashCode
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
    public List<Property> getChildren() {
        throw new UnsupportedOperationException("FieldValueProperty does not have child properties");
    }
}
