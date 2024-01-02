package mouse.hoi.gamefiles.unparser.property;

import mouse.hoi.gamefiles.parser.property.PropertyType;
import mouse.hoi.gamefiles.common.style.PrintStyle;

import java.util.Collection;
import java.util.List;

public interface OutputProperty {
    void setKey(String key);
    void setValue(String value);
    void setType(PropertyType propertyType);
    void setStyle(PrintStyle printStyle);
    void setPriority(int priority);
    int getPriority();
    String getKey();
    String getValue();
    PropertyType getType();
    PrintStyle getStyle();
    List<OutputProperty> getChildren();
    void addChild(OutputProperty newProperty);
    void addChildren(Collection<OutputProperty> newProperty);
    void setChildren(List<OutputProperty> children);
}
