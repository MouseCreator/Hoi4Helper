package mouse.hoi.parser.property;

import mouse.hoi.parser.property.input.Property;

import java.util.ArrayList;
import java.util.List;

public class ParametrizedBlockProperty<T extends BaseProperty> implements ParametrizedProperty<T> {
    private String key;
    private String value;
    private final List<T> children;

    public ParametrizedBlockProperty() {
        this.key = "";
        this.value = "";
        this.children = new ArrayList<>();
    }

    public ParametrizedBlockProperty(String key, String value) {
        this.key = key;
        this.value = value;
        children = new ArrayList<>();
    }

    public ParametrizedBlockProperty(String key, String value, List<T> properties) {
        this.key = key;
        this.value = value;
        this.children = new ArrayList<>(properties);
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChild(T child) {
        this.children.add(child);
    }

    @Override
    public boolean isBlock() {
        return true;
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
        return children;
    }

    @Override
    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append(key).append("=").append(value).append("{");
        for (T property : children) {
            builder.append(property.print());
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public PropertyType type() {
        return PropertyType.BLOCK;
    }
}
