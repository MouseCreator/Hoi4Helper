package mouse.hoi.developer.searcher;

import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.property.PropertyType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SeekerResultBuilder {
    private List<Property> activeProperties;

    private SeekerResultBuilder(List<Property> initial) {
        activeProperties = new ArrayList<>(initial);
    }

    public static SeekerResultBuilder seek(List<Property> properties) {
        return new SeekerResultBuilder(properties);
    }
    public SeekerResultBuilder under(String target) {
        List<Property> newActiveProperties = new ArrayList<>();
        searchFor(activeProperties, newActiveProperties, target::equals);
        activeProperties = newActiveProperties;
        return this;
    }

    public SeekerResultBuilder remove(Predicate<String> target) {
        activeProperties.removeIf(p -> target.test(p.getKey()));
        return this;
    }

    public SeekerResultBuilder under(Predicate<String> target) {
        List<Property> newActiveProperties = new ArrayList<>();
        searchFor(activeProperties, newActiveProperties, target);
        activeProperties = newActiveProperties;
        return this;
    }

    private void searchFor(List<Property> properties, List<Property> resultList, Predicate<String> target) {
        for (Property property : properties) {
            if (property.type()==PropertyType.SIMPLE) {
                continue;
            }
            if (target.test(property.getKey())) {
                resultList.addAll(property.getChildren());
            }
            if (property.isBlock()) {
                searchFor(property.getChildren(), resultList, target);
            }
        }
    }

    public SeekerResultBuilder dive(Predicate<String> toSkip) {
        activeProperties = skipProperties(activeProperties, toSkip);
        return this;
    }

    private List<Property> skipProperties(List<Property> properties, Predicate<String> toSkip) {
        List<Property> result = new ArrayList<>();
        for (Property property : properties) {
            if (isPropertyToSkip(property, toSkip)) {
                if (property.isBlock()) {
                    List<Property> children = property.getChildren();
                    result.addAll(skipProperties(children, toSkip));
                }
            } else {
                result.add(property);
            }
        }
        return result;
    }

    private boolean isPropertyToSkip(Property property, Predicate<String> toSkip) {
        if (property.type()== PropertyType.SIMPLE) {
            return true;
        }
        String key = property.getKey();
        return toSkip.test(key);
    }

    public List<Property> get() {
        return activeProperties;
    }


}
