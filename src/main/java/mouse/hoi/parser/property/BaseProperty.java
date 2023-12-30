package mouse.hoi.parser.property;

public interface BaseProperty {
    boolean isBlock();
    String getKey();
    String getValue();
    boolean isEmpty();
    String print();
    PropertyType type();
}
