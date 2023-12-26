package mouse.hoi.parser.property;

import java.util.List;

public interface Property {
    boolean isBlock();
    String getKey();
    String getValue();
    boolean isEmpty();
    List<Property> getChildren();
    String print();
}
