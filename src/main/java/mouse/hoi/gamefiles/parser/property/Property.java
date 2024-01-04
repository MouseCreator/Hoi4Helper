package mouse.hoi.gamefiles.parser.property;


import java.util.List;

public interface Property {
    boolean isBlock();
    String getKey();
    String getValue();
    boolean isEmpty();
    List<Property> getChildren();
    String print();
    PropertyType type();
    boolean hasKey(String target);
}
