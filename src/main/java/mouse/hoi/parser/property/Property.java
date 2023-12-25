package mouse.hoi.parser.property;

import java.util.List;

public interface Property {
    boolean isBlock();
    String getKey();
    String getValue();
    List<Property> getChildren();
}
