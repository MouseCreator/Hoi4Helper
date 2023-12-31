package mouse.hoi.gamefiles.unparser.property;

import mouse.hoi.gamefiles.unparser.style.PrintStyle;

import java.util.Collection;

public interface OutputProperty {
    PrintStyle getStyle();
    void addChild(OutputProperty newProperty);
    void addChildren(Collection<OutputProperty> newProperty);
}
