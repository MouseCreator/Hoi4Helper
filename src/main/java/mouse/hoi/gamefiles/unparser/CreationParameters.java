package mouse.hoi.gamefiles.unparser;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class CreationParameters {
    private final List<Annotation> fieldAnnotations;
    private final List<Annotation> classAnnotations;
    private String key;
    private String value;
    public CreationParameters() {
        fieldAnnotations = new ArrayList<>();
        classAnnotations = new ArrayList<>();
        key = "";
        value = "";
    }

    public List<Annotation> getFieldAnnotations() {
        return fieldAnnotations;
    }

    public List<Annotation> getClassAnnotations() {
        return classAnnotations;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Annotation> toAnnotations() {
        List<Annotation> list = new ArrayList<>();
        list.addAll(getFieldAnnotations());
        list.addAll(getClassAnnotations());
        return list;
    }
}
