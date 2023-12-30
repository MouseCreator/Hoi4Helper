package mouse.hoi.parser.property;


import java.util.List;

public interface ParametrizedProperty<T extends BaseProperty> extends BaseProperty {
    List<T> getChildren();;
}
