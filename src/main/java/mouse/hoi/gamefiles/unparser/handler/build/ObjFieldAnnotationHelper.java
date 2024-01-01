package mouse.hoi.gamefiles.unparser.handler.build;

import mouse.hoi.gamefiles.common.ParseHelper;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.unparser.handler.BuilderInitializer;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.property.OutputPropertyBuilder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class ObjFieldAnnotationHelper implements BuilderAnnotationHelper {

    private final ParseHelper parseHelper;
    private final BuilderInitializer builderInitializer;

    public ObjFieldAnnotationHelper(ParseHelper parseHelper, BuilderInitializer builderInitializer) {
        this.parseHelper = parseHelper;
        this.builderInitializer = builderInitializer;
    }

    @Override
    public List<OutputProperty> toProperties(Object model) {
        List<Field> objFields = parseHelper.getFieldsWithAnnotation(model, ObjField.class);
        HashMap<String, List<Field>> fieldsByKeys = toObjFieldMap(objFields);
        List<OutputProperty> result = new ArrayList<>();
        for (String key : fieldsByKeys.keySet()) {
            List<Field> fields = fieldsByKeys.get(key);
            List<OutputPropertyBuilder> builders = builderInitializer.initializeProperties(model, fields);
            result.addAll(builderInitializer.toProperty(model, builders));
        }
        return result;
    }

    private HashMap<String, List<Field>> toObjFieldMap(List<Field> fields) {
        HashMap<String, List<Field>> map = new HashMap<>();
        for (Field field : fields) {
            String key = field.getAnnotation(ObjField.class).text();
            List<Field> forKey = map.computeIfAbsent(key, k -> new ArrayList<>());
            forKey.add(field);
        }
        return map;
    }
}
