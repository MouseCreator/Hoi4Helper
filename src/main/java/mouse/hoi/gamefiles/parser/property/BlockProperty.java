package mouse.hoi.gamefiles.parser.property;


import lombok.ToString;
import java.util.List;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
@EqualsAndHashCode
@ToString
public class BlockProperty implements Property {
    private String key;
    private String value;
    private final List<Property> children;

    public BlockProperty(String key, String value) {
        this.key = key;
        this.value = value;
        children = new ArrayList<>();
    }

    public BlockProperty(String key, String value, List<Property> properties) {
        this.key = key;
        this.value = value;
        this.children = new ArrayList<>(properties);
    }

    public static BlockProperty withKey(String key) {
        return new BlockProperty(key, "");
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addChild(Property child) {
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
    public List<Property> getChildren() {
        return children;
    }

    @Override
    public String print() {
        StringBuilder builder = new StringBuilder();
        builder.append(key).append("=").append(value).append("{");
        for (Property property : children) {
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
